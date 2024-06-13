package com.project.basket.dto;

import com.project.basket.domain.BasketProduct;

public record BasketResponse(
	Long productId,
	Integer quantity
) {
	public static BasketResponse of(BasketProduct basketProduct) {
		return new BasketResponse(basketProduct.getProductId(), basketProduct.getQuantity());
	}
}