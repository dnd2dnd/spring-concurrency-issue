package com.project.order.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class BaseOrder {

	@Schema(description = "배달 주소", example = "서울특별시 성북구 성신여대 앞")
	private String deliveryAddress;

	@Schema(description = "기본 주소 여부", example = "true")
	private boolean defaultAddress;

}
