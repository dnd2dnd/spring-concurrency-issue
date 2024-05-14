package com.project.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.order.domain.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

	default Orders getById(Long orderId) {
		return findById(orderId).orElseThrow(
			() -> new IllegalArgumentException("Not Found ID : " + orderId));
	}
}
