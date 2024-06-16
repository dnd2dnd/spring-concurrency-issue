package com.project.cart.controller;

import java.util.List;

import com.project.cart.dto.CartRequest;
import com.project.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.cart.dto.CartResponse;

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