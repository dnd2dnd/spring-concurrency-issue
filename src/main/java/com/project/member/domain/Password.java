package com.project.member.domain;

import static com.project.member.MemberConstant.*;
import static lombok.AccessLevel.*;

import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Password {
	private static final int PASSWORD_LENGTH_LOWER_BOUND_INCLUSIVE = 8;
	private static final int PASSWORD_LENGTH_UPPER_BOUND_INCLUSIVE = 20;

	public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$])[a-zA-Z!@#$]+$";
	private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

	@Column(nullable = false, length = 200)
	private String password;

	private Password(String password) {
		this.password = password;
	}

	public static Password of(String password, PasswordEncoder passwordEncoder) {
		validateLength(password);
		validatePattern(password);
		String encoded = passwordEncoder.encode(password);
		return new Password(encoded);
	}

	private static void validateLength(String password) {
		if (PASSWORD_LENGTH_LOWER_BOUND_INCLUSIVE <= password.length()
			&& password.length() <= PASSWORD_LENGTH_UPPER_BOUND_INCLUSIVE) {
			return;
		}
		throw new IllegalArgumentException(PASSWORD_LENGTH_INVALID);
	}

	private static void validatePattern(String password) {
		if (!PASSWORD_PATTERN.matcher(password).find()) {
			throw new IllegalArgumentException(PASSWORD_FORMAT_INVALID);
		}
	}

	public void checkPassword(String rawPassword, PasswordEncoder passwordEncoder) {
		if(!passwordEncoder.matches(rawPassword, this.password)) {
			throw new IllegalArgumentException(PASSWORD_FORMAT_INVALID);
		}
	}
}
