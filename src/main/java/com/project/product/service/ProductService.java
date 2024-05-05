package com.project.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.product.domain.Product;
import com.project.product.dto.request.ProductRequest;
import com.project.product.dto.response.ProductResponse;
import com.project.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;

	public Long createProduct(ProductRequest productRequest) {
		Product product = Product.of(
			productRequest.name(),
			productRequest.price(),
			productRequest.amount(),
			productRequest.desc(),
			productRequest.category()
		);

		return productRepository.save(product).getId();
	}

	@Transactional(readOnly = true)
	public ProductResponse getProduct(Long productId) {
		return ProductResponse.from(productRepository.getById(productId));
	}

	public Long updateProduct(Long productId, ProductRequest productRequest) {
		Product product = productRepository.getById(productId);
		product.updateProduct(
			productRequest.name(),
			productRequest.price(),
			productRequest.amount(),
			productRequest.desc(),
			productRequest.category()
		);

		return product.getId();
	}
}
