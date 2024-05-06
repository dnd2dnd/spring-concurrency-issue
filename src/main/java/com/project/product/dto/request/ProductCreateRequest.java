package com.project.product.dto.request;

import static com.project.product.ProductConstant.*;

import com.project.product.domain.ProductCategory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductCreateRequest(
	@Schema(description = "상품명", example = "당근")
	@NotBlank(message = NAME_NOT_NULL)
	String name,

	@Schema(description = "가격", example = "10000")
	@PositiveOrZero(message = PRICE_IS_POSITIVE)
	Integer price,

	@Schema(description = "수량", example = "100000")
	@PositiveOrZero(message = AMOUNT_IS_POSITIVE)
	@Min(value = 10000, message = AMOUNT_IS_10000)
	Integer amount,

	@Schema(description = "설명", example = "신선하고 맛있는 바니바니 당근 당근~")
	String description,

	@Schema(description = "카테고리", example = "FRESH")
	ProductCategory category
) {
}
