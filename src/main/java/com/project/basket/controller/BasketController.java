package com.project.basket.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.basket.dto.BasketResponse;
import com.project.basket.service.BasketRedisService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {

	private final BasketRedisService basketRedisService;

	@GetMapping
	public ResponseEntity<List<BasketResponse>> getBasket(
		@RequestParam Long memberId
	) {
		List<BasketResponse> responses = basketRedisService.getHashOps(memberId);
		return ResponseEntity.ok(responses);
	}

	@PostMapping
	public ResponseEntity<Void> addProductByBasket(
		@RequestParam Long memberId,
		@RequestParam Long productId
	) {
		basketRedisService.setValue(memberId, productId);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity<Void> addAmountByProduct(
		@RequestParam Long memberId,
		@RequestParam Long productId,
		@RequestParam Integer amount
	) {
		basketRedisService.updateValue(memberId, productId, amount);
		return ResponseEntity.ok().build();
	}
}
