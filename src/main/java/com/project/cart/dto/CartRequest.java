package com.project.cart.dto;

public record CartRequest(
    Long userId,
    Long productId,
    Integer quantity
) {
}
