package com.project.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.order.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	OrderItem findByOrder_Id(String orderId);
}
