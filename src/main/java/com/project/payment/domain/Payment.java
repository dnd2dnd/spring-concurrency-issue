package com.project.payment.domain;

import com.project.common.BaseTime;
import com.project.member.domain.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter //TODO Test 해보기 위해 @Setter 사용했음, method로 바꿀 예정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private Long paymentId;

	@Column(nullable = false, name = "pay_type")
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType; //결제 type

	@Column(nullable = false)
	private Long amount; // 주문 금액

	@Column(nullable = false)
	private String orderName; //주문 이름

	@Column
	private String orderId; //주문 id

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "member_id")
	private Member member; //멤버

	@Column(unique = true)
	private String paymentKey; //결제 key

	@Column
	private String failReason; //실패 이유

	@Column(nullable = false)
	private boolean paySuccessYN; //성공 여부

	private Payment(Member member, PaymentType paymentType, Long amount, String orderId,
		String orderName, boolean paySuccessYN) {
		this.member = member;
		this.paymentType = paymentType;
		this.amount = amount;
		this.orderId = orderId;
		this.orderName = orderName;
		this.paySuccessYN = paySuccessYN;
	}

	public static Payment of(Member member, PaymentType paymentType, Long amount,
		String orderId, String orderName, boolean paySuccessYN) {
		return new Payment(member, paymentType, amount, orderId, orderName, paySuccessYN);
	}

}