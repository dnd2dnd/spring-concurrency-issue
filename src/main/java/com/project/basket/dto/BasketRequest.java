package com.project.basket.dto;

import lombok.Getter;

@Getter
public class BasketRequest {
	private Long memberId;
	private Long productId;
	private Integer quantity;

}
