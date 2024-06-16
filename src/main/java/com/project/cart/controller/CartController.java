package com.project.cart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.cart.dto.CartRequest;
import com.project.cart.dto.CartResponse;
import com.project.cart.service.CartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {
	private final CartService cartService;

	@GetMapping
	public ResponseEntity<List<CartResponse>> getCarts(
		@RequestParam Long userId
	) {
		List<CartResponse> responses = cartService.getCarts(userId);
		return ResponseEntity.ok(responses);
	}

	@PostMapping
	public ResponseEntity<Void> addProductByCart(
		@RequestBody CartRequest cartRequest
	) {
		cartService.addProductByCart(cartRequest);
		return ResponseEntity.ok().build();
	}
}