package com.project.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.product.domain.Product;
import com.project.product.dto.request.ProductCreateRequest;
import com.project.product.dto.response.ProductResponse;
import com.project.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
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

	@Transactional(readOnly = true)
	public ProductResponse getProduct(Long productId) {
		return ProductResponse.from(productRepository.getById(productId));
	}

}
