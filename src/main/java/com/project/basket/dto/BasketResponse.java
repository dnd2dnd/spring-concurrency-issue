package com.project.basket.dto;

import com.project.basket.domain.BasketAmount;

public record BasketResponse(
	Long productId,
	Integer amount
) {
	public static BasketResponse of(BasketAmount basketAmount) {
		return new BasketResponse(basketAmount.getProduct().getId(), basketAmount.getAmount());
	}
}
