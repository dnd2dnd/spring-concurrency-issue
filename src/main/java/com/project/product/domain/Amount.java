package com.project.product.domain;

import static com.project.product.ProductConstant.*;

import java.security.InvalidParameterException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Amount {

	@Column
	private Integer amount;

	private Amount(Integer amount) {
		this.amount = amount;
	}

	public static Amount from(Integer amount) {
		validatePositive(amount);
		validateMin(amount);
		return new Amount(amount);
	}

	private static void validatePositive(int amount) {
		if (amount < 0) {
			throw new InvalidParameterException(AMOUNT_IS_POSITIVE);
		}
	}

	private static void validateMin(int amount) {
		if (amount >= 10000) {
			throw new InvalidParameterException(AMOUNT_IS_10000);
		}
	}
}
