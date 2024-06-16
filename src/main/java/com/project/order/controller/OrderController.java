package com.project.order.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.order.dto.request.CartOrderRequest;
import com.project.order.dto.request.OrderRequest;
import com.project.order.dto.request.OrderRequestWrapper;
import com.project.order.dto.response.OrderResponse;
import com.project.order.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<Void> createOrder(
		@RequestParam Long userId,
		@Valid @RequestBody OrderRequest orderRequest
	) {
		String id = orderService.createOrder(userId, orderRequest);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/{id}")
			.buildAndExpand(id).toUri();

		return ResponseEntity.created(uri)
			.build();
	}

	@PostMapping("/cart")
	public ResponseEntity<Void> createCartOrder(
		@RequestParam Long userId,
		@Valid @RequestBody OrderRequestWrapper orderRequestWrapper
	) {
		String deliveryAddress = orderRequestWrapper.getDeliveryAddress();
		boolean defaultAddress = orderRequestWrapper.isDefaultAddress();
		List<CartOrderRequest> items = orderRequestWrapper.getCartOrderRequestList();
		orderService.createCartOrder(userId, deliveryAddress, defaultAddress, items);
		return ResponseEntity.ok().build();
	}

	@GetMapping("")
	public ResponseEntity<List<OrderResponse>> getOrderList(
		@RequestParam Long userId
	) {
		List<OrderResponse> responses = orderService.getOrderList(userId);
		return ResponseEntity.ok(responses);
	}
}