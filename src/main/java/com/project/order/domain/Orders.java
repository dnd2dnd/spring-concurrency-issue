package com.project.order.domain;

import com.project.member.domain.Member;
import com.project.product.domain.Amount;
import com.project.product.domain.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/***
 * - 주문 아이디 ,Long , NN, PK
 * - 회원 아이디, Long, FK
 * - 상품 아이디, Long, FK
 * - 결제 수단 (enum : 카드, 계좌이체)
 * - 상품 개수, Integer, NN
 * - 주문 결제 금액 , Integer, NN ->
 상품 아이디랑 개수 알면 계산할 수 있으니 굳이 저장할 필요가 있을까? response에서만 주면 된다면 컬럼 지워도 될
 * - 주문상태 (결제 완료, 입금 대기, 취소)
 * - 배송지 ? -> 회원 주소 도메인 따로 만들면 불러와야 할 것 같음
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	Product product;

	@Column
	PaymentType paymentType;

	@Embedded
	Amount amount;

	@Column
	OrderStatus orderStatus;

	@Column
	String deliveryAddress;

	private Orders(String deliveryAddress, Product product, Member member, PaymentType paymentType,
		Amount amount, OrderStatus orderStatus) {
		this.deliveryAddress = deliveryAddress;
		this.product = product;
		this.member = member;
		this.paymentType = paymentType;
		this.amount = amount;
		this.orderStatus = orderStatus;
	}

	public static Orders of(
		String deliveryAddress,
		Product product,
		Member member,
		PaymentType paymentType,
		Integer amount,
		OrderStatus orderStatus
	) {
		return new Orders(
			deliveryAddress,
			product,
			member,
			paymentType,
			Amount.from(amount),
			orderStatus
		);
	}

}
