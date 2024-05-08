package com.project.member.dto.request;

import static com.project.member.MemberConstant.*;

import com.project.member.domain.Password;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SignInRequest(
	@Schema(description = "이메일", example = "test@naver.com")
	@NotBlank(message = EMAIL_NOT_NULL)
	@Email(message = EMAIL_PATTERN_INVALID + " : ${validatedValue}")
	String email,

	@Schema(description = "비밀번호", example = "test123!@#$")
	@NotNull(message = PASSWORD_NOT_NULL)
	@Pattern(regexp = Password.PASSWORD_REGEX, message = PASSWORD_FORMAT_INVALID)
	String password
) {
}
