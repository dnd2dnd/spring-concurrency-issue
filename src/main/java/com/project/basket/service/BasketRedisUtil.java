package com.project.basket.service;

import com.project.basket.domain.BasketProduct;
import com.project.basket.domain.BasketRedis;
import com.project.basket.dto.BasketResponse;
import com.project.basket.exception.ProductAlreadyAddedException;
import com.project.basket.exception.ProductNotFoundException;
import com.project.product.domain.Product;
import com.project.product.domain.Stock;
import com.project.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class BasketRedisUtil {
	private final BasketRedis basketRedis;
	private final ProductRepository productRepository;

	public void setValue(Long key, Long productId) {
		Product product = productRepository.getById(productId);
		HashOperations<Long, Long, BasketProduct> hashOperations = basketRedis.getHashOperations();
		BasketProduct value = hashOperations.get(key, productId);
		if (value != null) {
			throw new ProductAlreadyAddedException();
		}
        Stock stock = product.getStock();
		hashOperations.put(key, productId,
			BasketProduct.of(product.getId(), stock.getTotalQuantity(), stock.getSalesQuantity(),1));
	}

	public void updateValue(Long key, Long productId, Integer value) {
		HashOperations<Long, Long, BasketProduct> hashOperations = basketRedis.getHashOperations();
		BasketProduct basketProduct = hashOperations.get(key, productId);
		if (basketProduct == null) {
			throw new ProductNotFoundException();
		}

		BasketProduct calculatedAmount = basketProduct.calculate(value);
		hashOperations.put(key, productId, calculatedAmount);
	}

	public List<BasketResponse> getHashOps(Long key) {
		HashOperations<Long, Long, BasketProduct> hashOperations = basketRedis.getHashOperations();

		List<BasketResponse> responses = new ArrayList<>();
		for (Long productId : hashOperations.keys(key)) {
			responses.add(BasketResponse.of(Objects.requireNonNull(hashOperations.get(key, productId))));
		}
		return responses;
	}
}
