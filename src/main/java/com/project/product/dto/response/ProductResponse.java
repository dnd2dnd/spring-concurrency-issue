package com.project.product.dto.response;

import com.project.product.domain.Product;
import com.project.product.domain.ProductCategory;
import com.project.product.domain.ProductStatus;

public record ProductResponse(
	Long productId,
	String name,
	Integer price,
	Integer stock,
	Integer sales,
	String desc,
	ProductCategory category,
	ProductStatus status
) {
	public static ProductResponse from(Product product) {
		return new ProductResponse(
			product.getId(),
			product.getName(),
			product.getPrice(),
			product.getTotalQuantity(),
			product.getSalesQuantity(),
			product.getDescription(),
			product.getCategory(),
			product.getStatus()
		);
	}
}