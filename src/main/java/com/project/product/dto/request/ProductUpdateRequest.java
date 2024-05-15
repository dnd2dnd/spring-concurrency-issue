package com.project.product.dto.request;

import static com.project.product.ProductConstant.*;

import com.project.product.domain.ProductCategory;
import com.project.product.domain.ProductStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductUpdateRequest(
	@Schema(description = "상품명", example = "당근")
	@NotBlank(message = NAME_NOT_NULL)
	String name,

	@Schema(description = "가격", example = "10000")
	@PositiveOrZero(message = PRICE_IS_POSITIVE)
	Integer price,

	@Schema(description = "재고", example = "100000")
	@PositiveOrZero(message = STOCK_IS_POSITIVE)
	@Min(value = 10000, message = STOCK_INIT_IS_10000)
	Integer stock,

	@Schema(description = "설명", example = "신선하고 맛있는 바니바니 당근 당근~")
	String description,

	@Schema(description = "카테고리", example = "FRESH")
	ProductCategory category,

	@Schema(description = "상태", example = "SELL")
	ProductStatus status,

	@Schema(description = "판매자 아이디", example = "1")
	Long sellerId
) {
}
