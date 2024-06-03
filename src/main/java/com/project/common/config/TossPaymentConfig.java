package com.project.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class TossPaymentConfig {
	public static final String baseUrl = "https://api.tosspayments.com/v1/payments/";
	@Value("${payment.toss.test_client_api_key}")
	private String testClientApiKey;

	@Value("${payment.toss.test_secret_api_key}")
	private String testSecretApiKey;

	@Value("${payment.toss.toss_success_url}")
	private String successfulUrl;

	@Value("${payment.toss.toss_fail_url}")
	private String failUrl;
}
