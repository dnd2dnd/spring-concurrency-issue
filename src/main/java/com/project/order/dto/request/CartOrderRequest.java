package com.project.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CartOrderRequest {
	@Schema(description = "상품 번호", example = "1")
	private Long productId;

}
