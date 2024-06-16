package com.project.order.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.cart.domain.CartProduct;
import com.project.cart.domain.CartRedis;
import com.project.order.domain.Order;
import com.project.order.domain.OrderItem;
import com.project.order.domain.OrderStatus;
import com.project.order.dto.request.CartOrderRequest;
import com.project.order.dto.request.OrderRequest;
import com.project.order.dto.response.OrderResponse;
import com.project.order.exception.InsufficientProductStockException;
import com.project.order.repository.OrderRepository;
import com.project.product.domain.Price;
import com.project.product.domain.Product;
import com.project.product.repository.ProductRepository;
import com.project.user.domain.Address;
import com.project.user.domain.User;
import com.project.user.repository.AddressRepository;
import com.project.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final AddressRepository addressRepository;
	private final ProductRepository productRepository;
	private final CartRedis cartRedis;

	public void createCartOrder(Long userId, String deliveryAddress, boolean defaultAddress,
		List<CartOrderRequest> cartOrderRequestList) {
		User user = userRepository.getById(userId);
		Address address = getAddress(userId, deliveryAddress, defaultAddress);
		HashOperations<Long, Long, CartProduct> hashOperations = cartRedis.getHashOperations();

		Order order = Order.of(user, address, OrderStatus.WAITING_FOR_PAYMENT);

		Long firstProductId = cartOrderRequestList.get(0).getProductId();
		Product firstProduct = productRepository.getById(firstProductId);
		String firstProductName = firstProduct.getName();

		for (CartOrderRequest request : cartOrderRequestList) {
			Long productId = request.getProductId();
			CartProduct cartProduct = hashOperations.get(userId, productId);

			Product product = productRepository.getById(productId);
			int productQuantity = product.getTotalQuantity() - product.getSalesQuantity();

			if (productQuantity < cartProduct.getQuantity()) {
				throw new InsufficientProductStockException();
			}

			OrderItem orderItem = OrderItem.of(order, product, cartProduct.getQuantity(),
				Price.calculatePrice(product.getPrice(), cartProduct.getQuantity()));

			order.addOrderItem(orderItem);
			// 장바구니에서 상품 삭제
			hashOperations.delete(userId, productId);
		}
		String orderName = createOrderName(firstProductName, cartOrderRequestList.size());
		order.addOrderName(orderName);
		orderRepository.save(order);
	}

	public String createOrder(Long userId, OrderRequest orderRequest) {
		User user = userRepository.getById(userId);
		Product product = productRepository.getById(orderRequest.getProductId());
		String orderName = product.getName();

		Address address = getAddress(userId, orderRequest.getDeliveryAddress(), orderRequest.isDefaultAddress());
		int productQuantity = product.getTotalQuantity() - product.getSalesQuantity();
		if (productQuantity < orderRequest.getQuantity()) {
			throw new InsufficientProductStockException();
		}
		Order order = Order.of(user, address, OrderStatus.WAITING_FOR_PAYMENT);

		order.addOrderItem(OrderItem.of(order, product, orderRequest.getQuantity(),
			Price.calculatePrice(product.getPrice(), orderRequest.getQuantity())));
		order.addOrderName(orderName);
		return orderRepository.save(order).getId();
	}

	private Address getAddress(Long userId, String deliveryAddress, boolean defaultAddress) {
		User user = userRepository.getById(userId);
		Optional<Address> optionalAddress = addressRepository.findByUser_IdAndDefaultAddress(
			userId, true);

		// 기본 배송지 주소 반환
		if (isDefaultAddress(optionalAddress, deliveryAddress)) {
			return optionalAddress.get();
		}
		//새로운 주소가 기본 배송지로 설정된 경우
		if (isAddressUpdateRequire(optionalAddress, defaultAddress)) {
			updateDefaultAddress(optionalAddress);
		}
		Address address = Address.of(user, deliveryAddress, defaultAddress);

		return addressRepository.save(address);
	}

	@Transactional(readOnly = true)
	public List<OrderResponse> getOrderList(Long userId) {
		List<Order> orders = orderRepository.findByUser_Id(userId);

		return orders.stream()
			.flatMap(order -> order.getOrderItemList().stream())
			.map(OrderResponse::of)
			.toList();
	}

	private boolean isDefaultAddress(Optional<Address> optionalAddress, String deliveryAddress) {
		return optionalAddress.isPresent() && deliveryAddress == null;
	}

	private boolean isAddressUpdateRequire(Optional<Address> optionalAddress, boolean isDefaultAddress) {
		return isDefaultAddress && optionalAddress.isPresent();
	}

	private void updateDefaultAddress(Optional<Address> optionalAddress) {
		optionalAddress.ifPresent(address -> address.changeDefaultAddress(false));
	}

	private String createOrderName(String productName, int OrderRequestListSize) {
		if (OrderRequestListSize > 1) {
			return productName + " 외 " + OrderRequestListSize + "개";
		}
		return productName;
	}
}