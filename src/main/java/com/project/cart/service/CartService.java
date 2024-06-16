package com.project.cart.service;

import com.project.cart.domain.CartProduct;
import com.project.cart.domain.CartRedis;
import com.project.cart.dto.CartRequest;
import com.project.cart.dto.CartResponse;
import com.project.product.domain.Product;
import com.project.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRedis cartRedis;
    private final ProductRepository productRepository;

    public void addProductByCart(CartRequest cartRequest) {
        Product product = productRepository.getById(cartRequest.productId());
        HashOperations<Long, Long, CartProduct> hashOperations = cartRedis.getHashOperations();

        CartProduct cartProduct = CartProduct.of(product, cartRequest.quantity());
        hashOperations.put(cartRequest.userId(), cartRequest.productId(), cartProduct);
    }

    public List<CartResponse> getCarts(Long key) {
        HashOperations<Long, Long, CartProduct> hashOperations = cartRedis.getHashOperations();

        return hashOperations.keys(key).stream()
                .map(productId -> CartResponse.of(Objects.requireNonNull(hashOperations.get(key, productId))))
                .toList();
    }
}
