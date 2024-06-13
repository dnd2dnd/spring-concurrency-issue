package com.project.order.dto.request;

import java.util.List;

import com.project.order.domain.BaseOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CartOrderRequest extends BaseOrder {
	@Schema(description = "상품 번호", example = "1")
	private Long productId;

	@Schema(description = "장바구니 리스트", example = "1,2")
	private List<CartOrderRequest> cartOrderRequestList;

}
