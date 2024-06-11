package com.project.payment.dto.response;

//Toss 결제 성공 DTO
// 결제 성공시 반환되는 정보 중 필요한 정보만 response
public record PaymentSuccessDto(
	String mid,
	String version,
	String paymentKey,
	String orderId,
	String orderName,
	String currency,
	String method,
	String totalAmount,
	String balanceAmount,
	String suppliedAmount,
	String vat,
	String status,
	String requestedAt,
	String approvedAt,
	String useEscrow,
	String cultureExpense,
	PaymentSuccessCardDto card,
	String type
) {
}
