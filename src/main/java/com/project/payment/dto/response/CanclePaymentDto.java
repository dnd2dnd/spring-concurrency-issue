package com.project.payment.dto.response;

import com.project.payment.domain.CanclePayment;

public record CanclePaymentDto(
	String paymentKey,
	Long cancleAmount,
	String orderId,
	String orderName,
	String cancleReason

) {
	public static CanclePaymentDto of(CanclePayment canclePayment) {
		return new CanclePaymentDto(
			canclePayment.getPaymentKey(),
			canclePayment.getCancleAmount(),
			canclePayment.getOrderId(),
			canclePayment.getOrderName(),
			canclePayment.getCancleReason()
		);
	}
}