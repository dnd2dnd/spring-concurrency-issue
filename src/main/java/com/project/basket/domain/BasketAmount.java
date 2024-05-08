package com.project.basket.domain;

import static com.project.product.ProductConstant.*;

import java.io.Serializable;
import java.security.InvalidParameterException;

import com.project.basket.exception.ProductAmountExceededException;
import com.project.product.domain.Product;

import lombok.Getter;

@Getter
public class BasketAmount implements Serializable {
	private final Product product;
	private final Integer amount;

	private BasketAmount(Product product, Integer amount) {
		this.product = product;
		this.amount = amount;
	}

	public static BasketAmount of(Product product, Integer amount) {
		validatePositive(amount);
		validateProductAmount(product.getAmount(), amount);
		return new BasketAmount(product, amount);
	}

	private static void validatePositive(int amount) {
		if (amount < 0) {
			throw new InvalidParameterException(AMOUNT_IS_POSITIVE);
		}
	}

	private static void validateProductAmount(int productAmount, int amount) {
		if (amount > productAmount) {
			throw new ProductAmountExceededException();
		}
	}

	public BasketAmount calculate(Integer value) {
		return BasketAmount.of(product, amount + value);
	}
}
