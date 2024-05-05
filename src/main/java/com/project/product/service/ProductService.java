package com.project.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.product.domain.Product;
import com.project.product.domain.ProductCategory;
import com.project.product.domain.ProductOrder;
import com.project.product.dto.request.ProductRequest;
import com.project.product.dto.response.ProductResponse;
import com.project.product.repository.ProductRepository;
import com.querydsl.core.types.Order;

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
			productRequest.category(),
			productRequest.status()
		);

		return productRepository.save(product).getId();
	}

	@Transactional(readOnly = true)
	public List<ProductResponse> getProducts(ProductCategory category, ProductOrder productOrder, Order order,
		String search) {
		return productRepository.findAllBySearch(category, productOrder, order, search);
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
			productRequest.category(),
			productRequest.status()
		);

		return product.getId();
	}

	public void deleteProduct(Long productId) {
		Product product = productRepository.getById(productId);
		// TODO 판매자만 삭제할 수 있도록 로직 추가 해야함
		productRepository.deleteById(product.getId());
	}
}
