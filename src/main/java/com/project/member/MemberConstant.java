package com.project.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConstant {

	public static final String EMAIL_NOT_NULL = "이메일은 빈 값일 수 없습니다.";
	public static final String EMAIL_PATTERN_INVALID = "올바르지 않은 이메일 형식입니다";
	public static final String EMAIL_FORMAT_INVALID = "닉네임 형식이 올바르지 않습니다.";
	public static final String EMAIL_NOT_FOUND = "해당 이메일을 찾을 수 없습니다.";

	public static final String NICKNAME_NOT_NULL = "닉네임은 빈 값일 수 없습니다.";
	public static final String NICKNAME_LENGTH_INVALID = "닉네임은 2글자에서 10글자 사이어야 합니다.";
	public static final String NICKNAME_FORMAT_INVALID = "닉네임 형식이 올바르지 않습니다.";
	public static final String NICKNAME_DUPLICATED = "이미 사용중인 닉네임 입니다";

	public static final String PASSWORD_LENGTH_INVALID = "비밀번호는 8글자에서 20글자 사이어야 합니다.";
	public static final String PASSWORD_NOT_NULL = "비밀번호는 빈 값일 수 없습니다.";
	public static final String PASSWORD_FORMAT_INVALID = "비밀번호 형식이 올바르지 않습니다.";

}
