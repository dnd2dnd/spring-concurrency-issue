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
public class MemberPassword {

	public static final String PASSWORD_REGEX = "^[a-zA-Z0-9!@#$]*$";
	private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

	@Column(nullable = false, length = 200)
	private String password;

	private MemberPassword(String password) {
		this.password = password;
	}

	public static MemberPassword of(String password, PasswordEncoder passwordEncoder) {
		validatePattern(password);
		String encoded = passwordEncoder.encode(password);
		return new MemberPassword(encoded);
	}

	private static void validatePattern(String password) {
		if (!PASSWORD_PATTERN.matcher(password).find()) {
			throw new IllegalArgumentException(PASSWORD_FORMAT_INVALID);
		}
	}
}
