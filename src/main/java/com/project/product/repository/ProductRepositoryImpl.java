package com.project.product.repository;

import static com.project.product.domain.QProduct.*;

import java.util.List;
import java.util.Objects;

import com.project.product.domain.ProductCategory;
import com.project.product.domain.ProductOrder;
import com.project.product.dto.response.ProductResponse;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<ProductResponse> findAllBySearch(ProductCategory category, ProductOrder productOrder, Order order,
		String search) {
		return jpaQueryFactory.select(
				Projections.constructor(ProductResponse.class, product.id, product.name, product.price,
					product.stock.totalQuantity, product.stock.salesQuantity, product.description,
					product.category, product.status)
			).from(product)
			.where(eqCategory(category))
			.orderBy(createProductSpecifier(productOrder, order))
			.fetch();
	}

	private BooleanExpression eqCategory(ProductCategory category) {
		if (Objects.isNull(category)) {
			return null;
		}
		return product.category.eq(category);
	}

	private OrderSpecifier<?> createProductSpecifier(ProductOrder productOrder, Order order) {
		// TODO 기본 정렬, 판매량이 많은 순으로 했음
		if (Objects.isNull(productOrder)) {
			return new OrderSpecifier<>(Order.DESC, product.stock.salesQuantity);
		}

		switch (productOrder) {
			case PRICE -> {
				return new OrderSpecifier<>(order, product.price.price);
			}
			case SALES -> {
				return new OrderSpecifier<>(order, product.stock.salesQuantity);
			}
		}
		return new OrderSpecifier<>(order, product.price.price);
	}
}