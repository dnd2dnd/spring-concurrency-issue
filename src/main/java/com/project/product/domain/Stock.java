package com.project.product.domain;

import static com.project.product.ProductConstant.*;

import java.io.Serializable;
import java.security.InvalidParameterException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock implements Serializable {

	@Column
	private Integer stock;

	private Stock(Integer stock) {
		this.stock = stock;
	}

	public static Stock from(Integer stock) {
		validatePositive(stock);
		validateMin(stock);
		return new Stock(stock);
	}

	private static void validatePositive(int stock) {
		if (stock < 0) {
			throw new InvalidParameterException(STOCK_IS_POSITIVE);
		}
	}

	private static void validateMin(int stock) {
		if (stock < 10000) {
			throw new InvalidParameterException(STOCK_INIT_IS_10000);
		}
	}

	public void updateStock(int stock) {
		this.stock = stock;
	}
}
