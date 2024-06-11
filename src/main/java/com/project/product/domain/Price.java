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
public class Price implements Serializable {

	@Column
	private Integer price;

	private Price(Integer price) {
		this.price = price;
	}

	public static Price from(Integer price) {
		validatePositive(price);
		return new Price(price);
	}

	private static void validatePositive(int price) {
		if (price < 0) {
			throw new InvalidParameterException(PRICE_IS_POSITIVE);
		}
	}
	
	public static Price calculatePrice(Integer price, Integer amount) {
		return new Price(price * amount);
	}
}
