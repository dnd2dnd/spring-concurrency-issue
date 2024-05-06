package com.project.product.dto.response;

import com.project.product.domain.Product;
import com.project.product.domain.ProductCategory;

public record ProductResponse(
	Long productId,
	String name,
	Integer price,
	Integer amount,
	Integer sales,
	String desc,
	ProductCategory category
) {
	public static ProductResponse from(Product product) {
		return new ProductResponse(
			product.getId(),
			product.getName(),
			product.getPrice(),
			product.getAmount(),
			product.getSales(),
			product.getDescription(),
			product.getCategory()
		);
	}
}
