package com.project.basket.exception;

import com.project.common.BusinessException;

public class ProductAmountExceededException extends BusinessException {
	public ProductAmountExceededException() {
		super("상품 수량을 초과할 수 없습니다.");
	}
}
