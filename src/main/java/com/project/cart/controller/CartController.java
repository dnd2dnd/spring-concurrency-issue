package com.project.cart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.cart.dto.CartResponse;
import com.project.cart.service.CartRedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {

	private final CartRedisUtil cartRedisUtil;

	@GetMapping
	public ResponseEntity<List<CartResponse>> getBasket(
		@RequestParam Long userId
	) {
		List<CartResponse> responses = cartRedisUtil.getHashOps(userId);
		return ResponseEntity.ok(responses);
	}

	@PostMapping
	public ResponseEntity<Void> addProductByBasket(
		@RequestParam Long userId,
		@RequestParam Long productId
	) {
		cartRedisUtil.setValue(userId, productId);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<Void> addAmountByProduct(
		@RequestParam Long userId,
		@RequestParam Long productId,
		@RequestParam Integer amount
	) {
		log.info("call1");
		cartRedisUtil.updateValue(userId, productId, amount);
		return ResponseEntity.ok().build();
	}
}