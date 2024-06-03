package com.project.payment.exception;

import com.project.common.BusinessException;

public class PaymentAmountException extends BusinessException {
	public PaymentAmountException() {
		super("주문 금액과 일치하지 않습니다.");
	}
}