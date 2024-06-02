package com.project.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.basket.domain.BasketProduct;
import com.project.basket.domain.BasketRedis;
import com.project.member.domain.Address;
import com.project.member.domain.Member;
import com.project.member.repository.AddressRepository;
import com.project.member.repository.MemberRepository;
import com.project.order.domain.BaseOrder;
import com.project.order.domain.Order;
import com.project.order.domain.OrderItem;
import com.project.order.domain.OrderStatus;
import com.project.order.dto.request.BasketOrderRequest;
import com.project.order.dto.request.OrderRequest;
import com.project.order.dto.response.OrderResponse;
import com.project.order.repository.OrderRepository;
import com.project.product.domain.Product;
import com.project.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final AddressRepository addressRepository;
	private final ProductRepository productRepository;
	private final BasketRedis basketRedis;

	public void createBasketOrder(Long memberId, List<BasketOrderRequest> basketOrderRequestList) {
		Member member = memberRepository.getById(memberId);
		Address address = getAddress(memberId, basketOrderRequestList.get(0));
		HashOperations<Long, Long, BasketProduct> hashOperations = basketRedis.getHashOperations();

		List<Order> orders = new ArrayList<>();

		for (BasketOrderRequest request : basketOrderRequestList) {
			Long productId = request.getProductId();
			BasketProduct basketProduct = hashOperations.get(memberId, productId);

			Product product = productRepository.getById(productId);
			String orderId = UUID.randomUUID().toString();
			// Order 생성
			Order order = Order.of(member, address, OrderStatus.WAITING_FOR_PAYMENT, orderId);
			OrderItem orderItem = OrderItem.of(order, product, basketProduct.getQuantity());
			order.addOrderItem(orderItem);

			// Order 저장
			Order savedOrder = orderRepository.save(order);
			orders.add(savedOrder);

			// 장바구니에서 상품 삭제
			hashOperations.delete(memberId, productId);
		}
	}

	public Long createOrder(Long memberId, OrderRequest orderRequest) {
		Member member = memberRepository.getById(memberId);
		Product product = productRepository.getById(orderRequest.getProductId());
		String orderId = UUID.randomUUID().toString();

		Address address = getAddress(memberId, orderRequest);

		Order order = Order.of(member, address, OrderStatus.WAITING_FOR_PAYMENT, orderId);

		order.addOrderItem(OrderItem.of(order, product, orderRequest.getQuantity()));

		return orderRepository.save(order).getId();
	}

	//TODO 메서드 수정해야 함 ( 똑같은 객체가 여러 번 저장되는 거 확인함 )
	private Address getAddress(Long memberId, BaseOrder orderRequest) {
		Member member = memberRepository.getById(memberId);
		Optional<Address> optionalAddress = addressRepository.findByMember_IdAndDefaultAddress(memberId,
			true);
		Address address = null;
		if (optionalAddress.isPresent() && orderRequest.getDeliveryAddress() == null) {
			// 기본 배송지 주소 반환
			log.info("defalut");
			return optionalAddress.get();
		}

		//새로운 주소가 기본 배송지로 설정된 경우
		if (orderRequest.isDefaultAddress() && optionalAddress.isPresent()) {
			log.info("new");
			optionalAddress.get().changeDefaultAddress(false);
			address = Address.of(member, orderRequest.getDeliveryAddress(), true);
		} else {
			address = Address.of(member, orderRequest.getDeliveryAddress(), orderRequest.isDefaultAddress());
		}

		return addressRepository.save(address);
	}

	@Transactional(readOnly = true)
	public List<OrderResponse> getOrderList(Long memberId) {
		List<Order> orders = orderRepository.findByMember_Id(memberId);

		return orders.stream()
			.flatMap(order -> order.getOrderItemList().stream())
			.map(OrderResponse::of)
			.toList();
	}
}
