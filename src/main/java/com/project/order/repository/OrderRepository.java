package com.project.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUser_Id(Long userId);

	Order findById(String orderId);
	
}
