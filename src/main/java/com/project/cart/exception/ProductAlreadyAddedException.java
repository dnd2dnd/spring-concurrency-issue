package com.project.cart.exception;

import com.project.common.BusinessException;

public class ProductAlreadyAddedException extends BusinessException {
	public ProductAlreadyAddedException() {
		super("이미 해당 상품을 장바구니에 추가했습니다");
	}
}
