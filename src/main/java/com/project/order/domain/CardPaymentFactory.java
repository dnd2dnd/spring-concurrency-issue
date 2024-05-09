package com.project.order.domain;

public class CardPaymentFactory extends PaymentFactory {

	@Override
	protected Payment createPaymentType() {
		return new CardPayment();
	}
}
