package com.project.user.dto.request;

import static com.project.user.UserConstant.*;

import com.project.user.domain.Nickname;
import com.project.user.domain.Password;

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
	@Pattern(regexp = Nickname.NICKNAME_REGEX, message = NICKNAME_FORMAT_INVALID)
	String nickname,

	@Schema(description = "비밀번호", example = "test123!@#$")
	@NotNull(message = PASSWORD_NOT_NULL)
	@Pattern(regexp = Password.PASSWORD_REGEX, message = PASSWORD_FORMAT_INVALID)
	String password

) {
}
