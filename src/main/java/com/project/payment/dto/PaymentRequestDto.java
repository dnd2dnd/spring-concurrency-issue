package com.project.payment.dto;

import com.project.payment.domain.PaymentType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PaymentRequestDto {
	@NotNull
	private PaymentType paymentType; // 결제 타입

	private Long amount; //결제 금액

	private String orderId; //주문 고유 번호

	@NotNull
	private String orderName; // 주문 이름

	private String successUrl; //성공 url
	private String failUrl; //실패 url

}
