package com.project.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import com.project.cart.domain.CartProduct;
import com.project.cart.domain.CartRedis;
import com.project.cart.dto.CartResponse;
import com.project.cart.exception.ProductAlreadyAddedException;
import com.project.cart.exception.ProductNotFoundException;
import com.project.product.domain.Product;
import com.project.product.domain.Stock;
import com.project.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartRedisUtil {
	private final CartRedis cartRedis;
	private final ProductRepository productRepository;

	public void setValue(Long key, Long productId) {
		Product product = productRepository.getById(productId);
		HashOperations<Long, Long, CartProduct> hashOperations = cartRedis.getHashOperations();
		CartProduct value = hashOperations.get(key, productId);
		if (value != null) {
			throw new ProductAlreadyAddedException();
		}
		Stock stock = product.getStock();
		hashOperations.put(key, productId,
			CartProduct.of(product.getId(), stock.getTotalQuantity(), stock.getSalesQuantity(), 1));
	}

	public void updateValue(Long key, Long productId, Integer value) {
		HashOperations<Long, Long, CartProduct> hashOperations = cartRedis.getHashOperations();
		CartProduct cartProduct = hashOperations.get(key, productId);
		if (cartProduct == null) {
			throw new ProductNotFoundException();
		}

		CartProduct calculatedAmount = cartProduct.calculate(value);
		hashOperations.put(key, productId, calculatedAmount);
	}

	public List<CartResponse> getHashOps(Long key) {
		HashOperations<Long, Long, CartProduct> hashOperations = cartRedis.getHashOperations();

		List<CartResponse> responses = new ArrayList<>();
		for (Long productId : hashOperations.keys(key)) {
			responses.add(CartResponse.of(Objects.requireNonNull(hashOperations.get(key, productId))));
		}
		return responses;
	}
}