package com.project.order.domain;

import static com.project.order.CardNumConstant.*;

import java.security.InvalidParameterException;
import java.util.regex.Pattern;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cards {

	private static final int CARD_NUM_REQUIRED_LENGTH = 19;
	public static final String CARD_NUM_REGEX = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";
	private static final Pattern CARD_NUM_PATTERN = Pattern.compile(CARD_NUM_REGEX);

	@Column(unique = true)
	private String cardNum;

	private Cards(String cardNum) {
		this.cardNum = cardNum;
	}

	public static Cards from(String cardNum) {
		validateLength(cardNum);
		validatePattern(cardNum);
		return new Cards(cardNum);
	}

	private static void validateLength(String cardNum) {
		if (CARD_NUM_REQUIRED_LENGTH != cardNum.length()) {
			throw new IllegalArgumentException(CARD_NUM_LENGTH_INVALID);
		}
	}

	private static void validatePattern(String cardNum) {
		if (!CARD_NUM_PATTERN.matcher(cardNum).matches()) {
			throw new InvalidParameterException(CARD_NUM_FORMAT_INVALID);
		}
	}
}

