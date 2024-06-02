package com.project.order.domain;

import java.util.ArrayList;
import java.util.List;

import com.project.common.BaseTime;
import com.project.member.domain.Address;
import com.project.member.domain.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import jakarta.persistence.Table;
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
@Table(name = "ORDERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(nullable = false, unique = true)
	String orderName; //고객한테 보여줄 주문 번호

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	List<OrderItem> orderItemList = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

	@Enumerated(EnumType.STRING)
	OrderStatus orderStatus;

	private Order(Member member, Address address, OrderStatus orderStatus,
		String orderId) {
		this.member = member;
		this.address = address;
		this.orderStatus = orderStatus;
		this.orderName = orderId;
	}

	public static Order of(Member member, Address address, OrderStatus orderStatus, String orderId) {
		return new Order(member, address, orderStatus, orderId);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItemList.add(orderItem);
		orderItem.setOrder(this);
	}

}
