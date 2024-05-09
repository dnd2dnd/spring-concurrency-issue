package com.project.order.domain;

public class CardPayment implements Payment {

	@Override
	public OrderStatus getOrderStatus() {
		return OrderStatus.PAYMENT_COMPLETED;
	}
}
