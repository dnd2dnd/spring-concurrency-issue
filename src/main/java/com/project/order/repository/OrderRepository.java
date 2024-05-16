package com.project.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	default Order getById(Long orderId) {
		return findById(orderId).orElseThrow(
			() -> new IllegalArgumentException("Not Found ID : " + orderId));
	}
}
