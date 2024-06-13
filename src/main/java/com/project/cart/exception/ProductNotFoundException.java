package com.project.cart.exception;

import com.project.common.BusinessException;

public class ProductNotFoundException extends BusinessException {
	public ProductNotFoundException() {
		super("해당 상품을 찾을 수 없습니다.");
	}
}
