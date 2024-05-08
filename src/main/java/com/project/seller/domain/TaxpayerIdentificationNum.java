package com.project.seller.domain;

import static com.project.seller.SellerConstant.*;
import static lombok.AccessLevel.*;

import java.security.InvalidParameterException;
import java.util.regex.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class TaxpayerIdentificationNum {
	private static final int TAXPAYER_IDENTIFICATION_NUM_REQUIRED_LENGTH =12;
	public static final String TAXPAYER_IDENTIFICATION_NUM_REGEX = "\\d{3}-\\d{2}-\\d{5}";
	private static final Pattern TAXPAYER_IDENTIFICATION_NUM_PATTERN = Pattern.compile(TAXPAYER_IDENTIFICATION_NUM_REGEX);

	@Column(nullable = false,unique = true)
	private String TaxpayerIdentificationNum;

	private TaxpayerIdentificationNum(String taxpayerIdentificationNum){
		this.TaxpayerIdentificationNum=taxpayerIdentificationNum;
	}

	public static TaxpayerIdentificationNum from(String taxpayerIdentificationNum){
		validatePattern(taxpayerIdentificationNum);
		validatateLength(taxpayerIdentificationNum);
		return new TaxpayerIdentificationNum(taxpayerIdentificationNum);
	}

	private static void validatateLength(String taxpayerIdentificationNum){
		if(TAXPAYER_IDENTIFICATION_NUM_REQUIRED_LENGTH ==taxpayerIdentificationNum.length()){
			return ;
		}
		throw new IllegalArgumentException(TAXPAYER_IDENTIFICATION_NUM_LENGTH_INVALID);
	}
	private static void validatePattern(String taxpayerIdentificationNum) {
		if (!TAXPAYER_IDENTIFICATION_NUM_PATTERN.matcher(taxpayerIdentificationNum).matches()) {
			throw new InvalidParameterException(TAXPAYER_IDENTIFICATION_NUM_FORMAT_INVALID);
		}
	}
}
