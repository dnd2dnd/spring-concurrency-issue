package com.project.order.domain;

public abstract class PaymentFactory {
	public Payment newInstance() {
		Payment payment = createPaymentType();
		payment.getOrderStatus();
		return payment;
	}

	protected abstract Payment createPaymentType();
}
