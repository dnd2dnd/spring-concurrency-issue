package com.project.payment.domain;

/** 결제 상태
 * PAY_COMPLETE : 결제 완료
 * PAY_CANCEL : 결제 취소
 * PAY_STANDBY : 결제 대기중 (default)*/
public enum PaymentStatus {
	PAY_COMPLETE, PAY_CANCEL, PAY_STANDBY
}
