package com.project.user.domain;

import static com.project.user.UserConstant.*;

import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

	private static final Pattern EMAIL_PATTERN = Pattern.compile(
		"^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+[.][0-9A-Za-z]+$");

	@Column(unique = true)
	private String email;

	private Email(String email) {
		this.email = email;
	}

	public static Email from(String email) {
		validateNull(email);
		validatePattern(email);
		return new Email(email);
	}

	private static void validateNull(String email) {
		if (!Objects.nonNull(email)) {
			throw new InvalidParameterException(EMAIL_NOT_NULL);
		}
	}

	private static void validatePattern(String email) {
		if (!EMAIL_PATTERN.matcher(email).find()) {
			throw new InvalidParameterException(EMAIL_FORMAT_INVALID);
		}
	}
}
