package com.project.payment.dto;

import java.time.LocalDateTime;

import com.project.payment.domain.Payment;
import com.project.payment.domain.PaymentType;

public record PaymentResponseDto(
	PaymentType payType,
	Long amount,
	String orderName,
	String orderId,
	String memberEmail, // TODO 알아보기 쉽게 customerEmail로 변경?
	String successUrl,
	String failUrl,
	// String failReason,
	// boolean cancelYN,
	// String cancelReason, // 단순 변심, 상태 불량 등 값 고정?
	LocalDateTime createdAt // 결제일자

) {
	public static PaymentResponseDto of(Payment payment, String successUrl, String failUrl) {
		return new PaymentResponseDto(
			payment.getPaymentType(),
			payment.getAmount(),
			payment.getOrderName(),
			payment.getOrderId(),
			payment.getMember().getEmail(),
			successUrl,
			failUrl,
			payment.getCreatedDate()
		);
	}
}
