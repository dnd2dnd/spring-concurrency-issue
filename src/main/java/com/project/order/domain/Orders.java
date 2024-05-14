package com.project.order.domain;

import java.util.ArrayList;
import java.util.List;

import com.project.common.BaseTime;
import com.project.member.domain.Address;
import com.project.member.domain.Member;
import com.project.product.domain.Amount;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/***
 * - 주문 아이디 ,Long , NN, PK
 * - 회원 아이디, Long, FK
 * - 상품 아이디, Long, FK
 * - 결제 수단 (card)
 * - 상품 개수, Integer, NN
 * - 주문 결제 금액 , Integer, NN ->
 상품 아이디랑 개수 알면 계산할 수 있으니 굳이 저장할 필요가 있을까? response에서만 주면 된다면 컬럼 지워도 될
 * - 주문상태 (결제 완료, 입금 대기, 취소)
 * - 배송지
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	Member member;

	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
	List<OrderItem> orderItemList = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "delivery_id")
	private Address address;

	@Embedded
	private Card card;

	@Embedded
	Amount amount;

	@Enumerated(EnumType.STRING)
	OrderStatus orderStatus;

	private Orders(Member member, Address address, Card card, Amount amount, OrderStatus orderStatus) {
		this.member = member;
		this.address = address;
		this.card = card;
		this.amount = amount;
		this.orderStatus = orderStatus;
	}

	public static Orders of(Member member, Address address, String cardNum, Integer amount, OrderStatus orderStatus) {
		return new Orders(member, address, Card.from(cardNum), Amount.from(amount), orderStatus);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItemList.add(orderItem);
		orderItem.setOrders(this);
	}
}
