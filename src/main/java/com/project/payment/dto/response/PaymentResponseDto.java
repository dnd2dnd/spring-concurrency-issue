package com.project.payment.dto.response;

import java.time.LocalDateTime;

import com.project.payment.domain.Payment;
import com.project.payment.domain.PaymentType;

public record PaymentResponseDto(
	PaymentType payType,
	Long amount,
	String orderName,
	String orderId,
	String memberEmail,
	String successUrl,
	String failUrl,
	LocalDateTime createdAt // 결제일자

) {
	public static PaymentResponseDto of(Payment payment, String successUrl, String failUrl) {
		return new PaymentResponseDto(
			payment.getPaymentType(),
			payment.getAmount(),
			payment.getOrderName(),
			payment.getOrderId(),
			payment.getUser().getEmail(),
			successUrl,
			failUrl,
			payment.getCreatedDate()
		);
	}
}