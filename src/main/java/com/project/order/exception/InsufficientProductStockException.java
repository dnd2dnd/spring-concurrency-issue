package com.project.order.exception;

import com.project.common.BusinessException;

public class InsufficientProductStockException extends BusinessException {
	public InsufficientProductStockException() {
		super("상품의 재고가 부족하여 주문할 수 없습니다..");
	}
}
