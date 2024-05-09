package com.project.order.dto.request;

import static com.project.product.ProductConstant.*;

import com.project.order.domain.PaymentType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;

/**
 - 배송지
 - 상품 id
 (기존 배송지가 있을 경우 불러오고 없을 경우 신규 배송지 입력)
 - 결제 수단 (카드 or 계좌이체 - 숫자만 가능)
 - 쿠폰
 (쿠폰이 있을 경우 쿠폰을 사용할 수 있다.)**/
public record OrderRequest(
	@Schema(description = "배송지", example = "서울특별시 영등포구 시흥대로 175길 2")
	String deliveryAddress,

	@Schema(description = "판매자 아이디", example = "1")
	Long productId,

	@Schema(description = "결제 수단", example = "카드")
	PaymentType paymentType,

	@Schema(description = "결제 정보", example = "카드 번호 or 계좌 번호")
	String paymentNumber,

	@Schema(description = "수량", example = "100000")
	@PositiveOrZero(message = AMOUNT_IS_POSITIVE)
	@Min(value = 10000, message = AMOUNT_IS_10000)
	Integer amount
	//TODO 쿠폰 추가
) {
}
