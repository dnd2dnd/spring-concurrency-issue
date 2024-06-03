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
	private PaymentType paymentType;

	@Column(nullable = false)
	private Long amount;

	@Column(nullable = false, unique = true)
	private String orderName;

	@Column
	private String orderId;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "member_id")
	private Member member; //멤버

	@Column(unique = true)
	private String paymentKey; //결제 key

	@Column
	private String failReason; //실패 이유

	@Column(nullable = false)
	private boolean paySuccessYN; //성공 여부

	@Column(nullable = false)
	private boolean cancelYN; //취소 여부

	@Column
	private String cancelReason; //취소 이유

	//TODO 주석 처리한 부분은 추후 메서드로 받아야하기 때문에 지워야 할 것 같음
	private Payment(Member member, PaymentType paymentType, Long amount, String orderId,
		String orderName, String successUrl, String failUrl, boolean paySuccessYN) {
		// , String paymentKey, String failReason, boolean cancelYN,
		// String cancelReason) {
		this.member = member;
		this.paymentType = paymentType;
		this.amount = amount;
		this.orderId = orderId;
		this.orderName = orderName;
		this.paySuccessYN = paySuccessYN;
		// this.paymentKey = paymentKey;
		// this.failReason = failReason;
		// this.cancelYN = cancelYN;
		// this.cancelReason = cancelReason;
	}

	public static Payment of(Member member, PaymentType paymentType, Long amount,
		String orderId, String orderName, String successUrl, String failUrl, boolean paySuccessYN) {
		// , String paymentKey, String failReason, boolean cancelYN,
		// String cancelReason) {
		return new Payment(member, paymentType, amount, orderId, orderName, successUrl, failUrl, paySuccessYN);
		//paymentKey, failReason, cancelYN, cancelReason);
	}

}