package com.project.cart.dto;

import com.project.cart.domain.CartProduct;

public record CartResponse(
	Long productId,
	Integer quantity
) {
	public static CartResponse of(CartProduct cartProduct) {
		return new CartResponse(cartProduct.getProductId(), cartProduct.getQuantity());
	}
}