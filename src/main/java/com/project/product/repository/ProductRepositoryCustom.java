package com.project.product.repository;

import java.util.List;

import com.project.product.domain.ProductCategory;
import com.project.product.domain.ProductOrder;
import com.project.product.dto.response.ProductResponse;
import com.querydsl.core.types.Order;

public interface ProductRepositoryCustom {
	List<ProductResponse> findAllBySearch(ProductCategory category, ProductOrder productOrder, Order order,
		String search);
}
