package com.project.member.dto;

import static com.project.member.MemberConstant.*;

import com.project.member.domain.MemberNickname;
import com.project.member.domain.MemberPassword;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(

	@Schema(description = "이메일", example = "test@naver.com")
	@NotBlank(message = EMAIL_NOT_NULL)
	@Email(message = EMAIL_PATTERN_INVALID + " : ${validatedValue}")
	String email,

	@Schema(description = "닉네임", example = "닉네임")
	@NotNull(message = NICKNAME_NOT_NULL)
	@Size(min = 2, max = 10, message = NICKNAME_LENGTH_INVALID)
	@Pattern(regexp = MemberNickname.NICKNAME_REGEX, message = NICKNAME_FORMAT_INVALID)
	String nickname,

	@Schema(description = "비밀번호", example = "test123!@#$")
	@NotNull(message = PASSWORD_NOT_NULL)
	@Pattern(regexp = MemberPassword.PASSWORD_REGEX, message = PASSWORD_FORMAT_INVALID)
	String password

) {
}
