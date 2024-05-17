package com.project.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByMember_Id(Long memberId);

	default Order getById(Long orderId) {
		return findById(orderId).orElseThrow(
			() -> new IllegalArgumentException("Not Found ID : " + orderId));
	}
}
