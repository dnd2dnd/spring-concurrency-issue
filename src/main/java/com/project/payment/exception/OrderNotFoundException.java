package com.project.payment.exception;

import com.project.common.BusinessException;

public class OrderNotFoundException extends BusinessException {
	public OrderNotFoundException() {
		super("해당 주문 내역을 찾을 수 없습니다.");
	}
}