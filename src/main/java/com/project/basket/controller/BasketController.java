package com.project.basket.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.basket.dto.BasketRequest;
import com.project.basket.dto.BasketResponse;
import com.project.basket.service.BasketRedisUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {

	private final BasketRedisUtil basketRedisUtil;

	@GetMapping
	public ResponseEntity<List<BasketResponse>> getBasket(
		@RequestParam Long memberId
	) {
		List<BasketResponse> responses = basketRedisUtil.getHashOps(memberId);
		return ResponseEntity.ok(responses);
	}

	@PostMapping
	public ResponseEntity<Void> addProductByBasket(
		@RequestBody BasketRequest basketRequest
	) {
		basketRedisUtil.setValue(basketRequest);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<Void> addAmountByProduct(
		@RequestParam Long memberId,
		@RequestParam Long productId,
		@RequestParam Integer amount
	) {
		basketRedisUtil.updateValue(memberId, productId, amount);
		return ResponseEntity.ok().build();
	}
}
