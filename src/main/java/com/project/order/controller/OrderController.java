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

import com.project.order.dto.request.BasketOrderRequest;
import com.project.order.dto.request.OrderRequest;
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
		@RequestParam Long memberId,
		@Valid @RequestBody OrderRequest orderRequest
	) {
		String id = orderService.createOrder(memberId, orderRequest);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/{id}")
			.buildAndExpand(id).toUri();

		return ResponseEntity.created(uri)
			.build();
	}

	//TODO basket 이름 유지할껀지?
	@PostMapping("/basket")
	public ResponseEntity<Void> createBasketOrder(
		@RequestParam Long memberId,
		@Valid @RequestBody List<BasketOrderRequest> basketOrderRequestList
	) {
		orderService.createBasketOrder(memberId, basketOrderRequestList);
		return ResponseEntity.ok().build();
	}

	@GetMapping("")
	public ResponseEntity<List<OrderResponse>> getOrderList(
		@RequestParam Long memberId
	) {
		List<OrderResponse> responses = orderService.getOrderList(memberId);
		return ResponseEntity.ok(responses);
	}
}
