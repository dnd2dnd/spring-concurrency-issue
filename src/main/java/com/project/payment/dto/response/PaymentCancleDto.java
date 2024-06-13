package com.project.payment.dto.response;

import com.project.payment.domain.CanclePayment;

public record PaymentCancleDto(
	String paymentKey,
	Long cancleAmount,
	String orderId,
	String orderName,
	String cancleReason

) {
	public static PaymentCancleDto of(CanclePayment canclePayment) {
		return new PaymentCancleDto(
			canclePayment.getPaymentKey(),
			canclePayment.getCancleAmount(),
			canclePayment.getOrderId(),
			canclePayment.getOrderName(),
			canclePayment.getCancleReason()
		);
	}
}