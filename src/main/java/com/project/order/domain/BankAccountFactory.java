package com.project.order.domain;

public class BankAccountFactory extends PaymentFactory {

	@Override
	protected Payment createPaymentType() {
		return new BankAccountPayment();
	}
}
