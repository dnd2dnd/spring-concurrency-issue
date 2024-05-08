package com.project.product.service;

import org.springframework.stereotype.Service;

import com.project.product.repository.ProductRepository;
import com.project.product.domain.Product;
import com.project.product.dto.request.ProductCreateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;

	public Long createProduct(ProductCreateRequest productCreateRequest) {
		Product product = Product.of(
			productCreateRequest.name(),
			productCreateRequest.price(),
			productCreateRequest.amount(),
			productCreateRequest.desc(),
			productCreateRequest.category()
		);
		return productRepository.save(product).getId();
	}
}
