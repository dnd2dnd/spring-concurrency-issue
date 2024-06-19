package com.project.order.dto.request;

import static com.project.product.ProductConstant.*;

import com.project.order.domain.BaseOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class OrderRequest extends BaseOrder {

	@Schema(description = "유저 아이", example = "1")
	private Long userId;

	@Schema(description = "상품 번호", example = "1")
	private Long productId;
	@Schema(description = "수량", example = "2")
	@PositiveOrZero(message = STOCK_IS_POSITIVE)
	private Integer quantity;

}
