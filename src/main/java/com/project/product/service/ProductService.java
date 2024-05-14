package com.project.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.product.domain.Product;
import com.project.product.domain.ProductCategory;
import com.project.product.domain.ProductOrder;
import com.project.product.dto.request.ProductCreateRequest;
import com.project.product.dto.request.ProductUpdateRequest;
import com.project.product.dto.response.ProductResponse;
import com.project.product.repository.ProductRepository;
import com.project.seller.domain.Seller;
import com.project.seller.repository.SellerRepository;
import com.querydsl.core.types.Order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final SellerRepository sellerRepository;

	public Long createProduct(@Valid ProductCreateRequest productCreateRequest) {
		Seller seller = sellerRepository.getById(productCreateRequest.sellerId());
		Product product = Product.of(
			productCreateRequest.name(),
			productCreateRequest.price(),
			productCreateRequest.stock(),
			productCreateRequest.description(),
			productCreateRequest.category(),
			seller
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

	public Long updateProduct(Long productId, ProductUpdateRequest productUpdateRequest) {
		Seller seller = sellerRepository.getById(productUpdateRequest.sellerId());
		Product product = productRepository.findByIdAndSeller(productId, seller);
		product.updateProduct(
			productUpdateRequest.name(),
			productUpdateRequest.price(),
			productUpdateRequest.description(),
			productUpdateRequest.category(),
			productUpdateRequest.status()
		);
		product.updateStock(productUpdateRequest.stock());

		return product.getId();
	}

	public void deleteProduct(Long productId, Long sellerId) {
		Seller seller = sellerRepository.getById(sellerId);
		Product product = productRepository.findByIdAndSeller(productId, seller);
		productRepository.deleteById(product.getId());
	}
}
