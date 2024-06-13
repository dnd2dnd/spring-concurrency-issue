package com.project.payment.domain;

import com.project.user.domain.User;

import jakarta.persistence.Column;
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

/**
 * approved_at : 승인 시점
 * cancle_amount : 승인 금액
 * cancle_reason : 취소 사유
 * card_company: 카드사
 * order_name: 주문 번호
 * payment_key : 주문 키
 * receipt_url : 영수증 url
 * requested_at : 요청 일자
 * member_id : 고객 id
 * */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CanclePayment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private User user;

	@Column(nullable = false)
	private String orderName;

	@Column
	private String orderId;

	@Column(nullable = false)
	private String paymentKey;

	@Column
	private Long cancleAmount;

	@Column
	private String cancleReason;

	private CanclePayment(User user, String paymentKey, Long amount, String orderId,
		String orderName, String cancleReason) {
		this.user = user;
		this.paymentKey = paymentKey;
		this.cancleAmount = amount;
		this.orderId = orderId;
		this.orderName = orderName;
		this.cancleReason = cancleReason;

	}

	public static CanclePayment of(User user, String paymentKey, Long amount, String orderId,
		String orderName, String cancleReason) {
		return new CanclePayment(user, paymentKey, amount, orderId, orderName, cancleReason);
	}

}