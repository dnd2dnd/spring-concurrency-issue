package com.project.basket.domain;

import static com.project.product.ProductConstant.*;

import java.io.Serializable;
import java.security.InvalidParameterException;

import com.project.basket.exception.ProductQuantityExceededException;

import lombok.Getter;

@Getter
public class BasketProduct implements Serializable {
	private final Long productId;
	private final Integer stock;
	private final Integer sales;
	private final Integer quantity;

	private BasketProduct(Long productId, Integer stock, Integer sales, Integer quantity) {
		this.productId = productId;
		this.stock = stock;
		this.sales = sales;
		this.quantity = quantity;
	}

	public static BasketProduct of(Long productId, int stock, int sales, Integer quantity) {
		int leftQuantity = stock - sales;
		validatePositive(quantity);
		validateProductAmount(leftQuantity, quantity);
		return new BasketProduct(productId, leftQuantity, sales, quantity);
	}

	private static void validatePositive(int quantity) {
		if (quantity < 0) {
			throw new InvalidParameterException(STOCK_IS_POSITIVE);
		}
	}

	private static void validateProductAmount(int productQuantity, int quantity) {
		if (quantity > productQuantity) {
			throw new ProductQuantityExceededException();
		}
	}

	public BasketProduct calculate(Integer value) {
		return BasketProduct.of(productId, stock, sales, quantity + value);
	}
}