package com.project.cart.dto;

import lombok.Getter;

@Getter
public class CartRequest {
	private Long userId;
	private Long productId;
	private Integer quantity;

}
