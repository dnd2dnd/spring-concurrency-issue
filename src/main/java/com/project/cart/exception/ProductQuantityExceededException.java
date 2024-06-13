package com.project.cart.exception;

import com.project.common.BusinessException;

public class ProductQuantityExceededException extends BusinessException {
	public ProductQuantityExceededException() {
		super("상품 수량을 초과할 수 없습니다.");
	}
}
