package com.project.seller.dto.request;

import static com.project.seller.SellerConstant.*;
import static com.project.seller.domain.TaxpayerIdentificationNum.*;
import static com.project.user.UserConstant.*;

import com.project.user.domain.Password;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SellerSignUpReuqest(
	@Schema(description = "이메일", example = "buisiness@naver.com")
	@NotBlank(message = EMAIL_NOT_NULL)
	@Email(message = EMAIL_PATTERN_INVALID + " : ${validatedValue}")
	String email,

	@Schema(description = "비밀번호", example = "test123!@#$")
	@NotNull(message = PASSWORD_NOT_NULL)
	@Pattern(regexp = Password.PASSWORD_REGEX, message = PASSWORD_FORMAT_INVALID)
	String password,

	@Schema(description = "사업자번호", example = "111-11-11111")
	@Pattern(regexp = TAXPAYER_IDENTIFICATION_NUM_REGEX,
		message = TAXPAYER_IDENTIFICATION_NUM_FORMAT_INVALID)
	@NotNull(message = TAXPAYER_IDENTIFICATION_NUM_NOT_NULL)
	String taxpayerIdentificationNum,

	@Schema(description = "업체명", example = "하루야채")
	@NotNull(message = COMPANY_NAME_NOT_NULL)
	String companyName,

	@Schema(description = "업체주소", example = "서울특별시 영등포구 1111길 하루야채")
	@NotNull(message = BUSINESS_LOCATION_NOT_NULL)
	String businessLocation
) {
}
