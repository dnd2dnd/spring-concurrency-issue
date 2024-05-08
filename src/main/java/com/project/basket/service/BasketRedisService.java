package com.project.basket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.project.basket.domain.BasketAmount;
import com.project.basket.dto.BasketResponse;
import com.project.basket.exception.ProductAlreadyAddedException;
import com.project.basket.exception.ProductNotFoundException;
import com.project.product.domain.Product;
import com.project.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BasketRedisService {
	private final RedisTemplate<Long, Long> redisTemplate;
	private final ProductRepository productRepository;

	public void setValue(Long key, Long productId) {
		Product product = productRepository.getById(productId);
		HashOperations<Long, Long, BasketAmount> hashOperations = redisTemplate.opsForHash();
		BasketAmount value = hashOperations.get(key, productId);
		if (value != null) {
			throw new ProductAlreadyAddedException();
		}
		hashOperations.put(key, productId, BasketAmount.of(product, 1));
		expireValues(key, 5, TimeUnit.MINUTES);
	}

	public void updateValue(Long key, Long productId, Integer value) {
		HashOperations<Long, Long, BasketAmount> hashOperations = redisTemplate.opsForHash();
		BasketAmount amount = hashOperations.get(key, productId);
		if (amount == null) {
			throw new ProductNotFoundException();
		}

		BasketAmount calculatedAmount = amount.calculate(value);
		hashOperations.put(key, productId, calculatedAmount);
		expireValues(key, 5, TimeUnit.MINUTES);
	}

	public List<BasketResponse> getHashOps(Long key) {
		HashOperations<Long, Long, Integer> hashOperations = redisTemplate.opsForHash();
		List<BasketResponse> responses = new ArrayList<>();
		for (Long productId : hashOperations.keys(key)) {
			responses.add(new BasketResponse(productId, hashOperations.get(key, productId)));
		}
		return responses;
	}

	public void expireValues(Long key, int timeout, TimeUnit timeUnit) {
		redisTemplate.expire(key, timeout, timeUnit);
	}
}
