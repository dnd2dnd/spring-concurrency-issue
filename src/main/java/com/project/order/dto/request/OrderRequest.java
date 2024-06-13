package com.project.order.dto.request;

import static com.project.product.ProductConstant.*;

import com.project.order.domain.BaseOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

/**
 - 배송지
 - 상품 id
 (기존 배송지가 있을 경우 불러오고 없을 경우 신규 배송지 입력)
 - 결제 수단 (카드 or 계좌이체 - 숫자만 가능)
 - 쿠폰
 (쿠폰이 있을 경우 쿠폰을 사용할 수 있다.)**/
@Getter
public class OrderRequest extends BaseOrder {
	@Schema(description = "상품 번호", example = "1")
	private Long productId;

	@Schema(description = "수량", example = "2")
	@PositiveOrZero(message = STOCK_IS_POSITIVE)
	private Integer quantity;

}
