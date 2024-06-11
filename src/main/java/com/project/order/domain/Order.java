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
 * - 주문상태 (결제 완료, 입금 대기, 취소)
 * - 배송지
 */
@Entity
@Table(name = "ORDERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "order_id")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(nullable = false)
	String orderName; // 주문 이름

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	List<OrderItem> orderItemList = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

	@Enumerated(EnumType.STRING)
	OrderStatus orderStatus;

	private Order(Member member, Address address, OrderStatus orderStatus) {
		this.member = member;
		this.address = address;
		this.orderStatus = orderStatus;
	}

	public static Order of(Member member, Address address, OrderStatus orderStatus) {
		return new Order(member, address, orderStatus);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItemList.add(orderItem);
		orderItem.setOrder(this);
	}

	public void addOrderName(String orderName) {
		this.orderName = orderName;
	}

	public static void updateOrderStatus(Order order, OrderStatus orderStatus) {
		order.orderStatus = orderStatus;
	}

}
