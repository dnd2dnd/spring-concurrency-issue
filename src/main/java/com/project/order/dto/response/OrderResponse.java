package com.project.order.dto.response;

import com.project.order.domain.OrderItem;
import com.project.order.domain.OrderStatus;

public record OrderResponse(
	Long id,
	Long productId,
	String name,
	Integer price,
	OrderStatus orderStatus
) {
	public static OrderResponse of(OrderItem orderItem) {
		return new OrderResponse(
			orderItem.getId(),
			orderItem.getProduct().getId(),
			orderItem.getProduct().getName(),
			orderItem.getProduct().getPrice(),
			orderItem.getOrder().getOrderStatus()
		);
	}
}
