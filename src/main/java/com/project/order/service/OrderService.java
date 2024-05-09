package com.project.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.member.domain.Member;
import com.project.member.repository.MemberRepository;
import com.project.order.domain.BankAccountFactory;
import com.project.order.domain.CardPaymentFactory;
import com.project.order.domain.Orders;
import com.project.order.domain.Payment;
import com.project.order.domain.PaymentFactory;
import com.project.order.domain.PaymentType;
import com.project.order.dto.request.OrderRequest;
import com.project.order.repository.OrderRepository;
import com.project.product.domain.Product;
import com.project.product.repository.ProductRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
	private final OrderRepository orderRepository;

	private final ProductRepository productRepository;

	private final MemberRepository memberRepository;

	public Long createOrder(Long memberId, @Valid OrderRequest orderRequest) {
		Member member = memberRepository.getById(memberId);
		Product product = productRepository.getById(orderRequest.productId());
		PaymentFactory pf;
		Payment payment;
		if (orderRequest.paymentType().equals(PaymentType.CREDIT_CARD)) {
			pf = new CardPaymentFactory();
			payment = pf.newInstance();
		} else {
			pf = new BankAccountFactory();
			payment = pf.newInstance();
		}
		Orders orders = Orders.of(
			orderRequest.deliveryAddress(),
			product,
			member,
			orderRequest.paymentType(),
			orderRequest.amount(),
			payment.getOrderStatus()
		);

		return orderRepository.save(orders).getId();
	}
}
