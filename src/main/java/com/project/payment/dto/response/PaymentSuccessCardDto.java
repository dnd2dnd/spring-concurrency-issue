package com.project.payment.dto.response;

// Toss 결제 성공시 card 정보 반환 Dto
public class PaymentSuccessCardDto {
	String company; // 회사명
	String number; // 카드번호
	String installmentPlanMonths; // 할부 개월
	String isInterestFree;
	String approveNo; // 승인번호
	String useCardPoint; // 카드 포인트 사용 여부 (카드 포인트는 어떻게 할까?)
	String cardType; // 카드 타입
	String ownerType; // 소유자 타입
	String acquireStatus; // 승인 상태
	String receiptUrl; // 영수증 URL (필요할까?)
}
