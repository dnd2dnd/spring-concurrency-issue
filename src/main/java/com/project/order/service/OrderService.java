package com.project.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.member.domain.Address;
import com.project.member.domain.Member;
import com.project.member.repository.AddressRepository;
import com.project.member.repository.MemberRepository;
import com.project.order.domain.OrderItem;
import com.project.order.domain.OrderStatus;
import com.project.order.domain.Orders;
import com.project.order.dto.request.OrderRequest;
import com.project.order.repository.OrderRepository;
import com.project.product.domain.Product;

import jakarta.validation.Valid;
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

	public Long createOrder(Long memberId, @Valid OrderRequest orderRequest) {
		Member member = memberRepository.getById(memberId);
		Address address;
		//TODO 효울적인 코드가 없을까?
		// 기본 배송지 true 값이면 기본 배송지로 보여주고
		// Or 신규 배송지일경우 신규 배송지 입력 받은 값 보여주기
		if (orderRequest.defaultAddress()) {
			address = addressRepository.findByMemberIdAndDefaultAddress(memberId, true);
		} else {
			address = Address.of(orderRequest.deliveryAddress(), orderRequest.defaultAddress());
		}
		// 새로운 주문 객체 생성
		Orders orders = Orders.of(member, address, orderRequest.cardNum(), orderRequest.amount(),
			OrderStatus.WAITING_FOR_PAYMENT);
		// 주문에 상품 추가
		for (Product product : orderRequest.productList()) {
			orders.addOrderItem(OrderItem.of(orders, product, orderRequest.amount()));
		}
		return orderRepository.save(orders).getId();
	}
}
