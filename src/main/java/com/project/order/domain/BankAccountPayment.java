package com.project.order.domain;

public class BankAccountPayment implements Payment {
	@Override
	public OrderStatus getOrderStatus() {
		//TODO 상태를 갖고 오는게 아니라 아예 객체 생성으로 업뎃해볼까
		return OrderStatus.WAITING_FOR_PAYMENT;
	}
}
