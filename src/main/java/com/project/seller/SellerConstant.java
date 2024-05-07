package com.project.seller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SellerConstant {
	public static final String TAXPAYER_IDENTIFICATION_NUM_NOT_NULL = "사업자 등록번호는 빈 값일 수 없습니다.";
	public static final String TAXPAYER_IDENTIFICATION_NUM_LENGTH_INVALID = "사업자 등록번호는 10자리 수입니다.";
	public static final String TAXPAYER_IDENTIFICATION_NUM_FORMAT_INVALID="사업자 등록번호 형식이 올바르지 않습니다.";

	public static final String COMPANY_NAME_NOT_NULL = "업체명은 빈 값일 수 없습니다.";
	public static final String BUISINESS_LOCATION_NOT_NULL = "업체 주소는 빈 값일 수 없습니다.";
}
