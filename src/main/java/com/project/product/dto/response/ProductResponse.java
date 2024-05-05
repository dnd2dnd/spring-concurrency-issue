package com.project.product.dto.response;

import com.project.product.domain.Amount;
import com.project.product.domain.Price;
import com.project.product.domain.Product;

public record ProductResponse(
	Long productId,
	String name,
	Price price,
	Amount amount,
	String desc
) {
	public static ProductResponse from(Product product) {
		return new ProductResponse(
			product.getId(),
			product.getName(),
			product.getPrice(),
			product.getAmount(),
			product.getDesc()
		);
	}
}
