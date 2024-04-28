package com.project.member.domain;

import static lombok.AccessLevel.*;

import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MemberNickname {

	private static final int NICKNAME_LENGTH_LOWER_BOUND_INCLUSIVE = 2;
	private static final int NICKNAME_LENGTH_UPPER_BOUND_INCLUSIVE = 10;

	private static final String NICKNAME_LENGTH_INVALID = "닉네임은 2글자에서 10글자 사이어야 합니다.";
	private static final String NICKNAME_FORMAT_INVALID = "닉네임 형식이 올바르지 않습니다.";

	public static final String NICKNAME_REGEX = "^[가-힣a-zA-Z0-9]*$";
	private static final Pattern NICKNAME_PATTERN = Pattern.compile(NICKNAME_REGEX);

	@Column(nullable = false, length = 10)
	private String nickname;

	private MemberNickname(String nickname) {
		this.nickname = nickname;
	}

	public static MemberNickname from(String nickname) {
		nickname = nickname.trim();
		validateLength(nickname);
		validatePattern(nickname);
		return new MemberNickname(nickname);
	}

	private static void validateLength(String nickname) {
		if (NICKNAME_LENGTH_LOWER_BOUND_INCLUSIVE <= nickname.length()
			&& nickname.length() <= NICKNAME_LENGTH_UPPER_BOUND_INCLUSIVE) {
			return;
		}
		throw new IllegalArgumentException(NICKNAME_LENGTH_INVALID);
	}

	private static void validatePattern(String nickname) {
		if (NICKNAME_PATTERN.matcher(nickname).find()) {
			return;
		}
		throw new IllegalArgumentException(NICKNAME_FORMAT_INVALID);
	}
}
