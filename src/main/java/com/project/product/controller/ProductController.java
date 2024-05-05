package com.project.product.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.product.domain.ProductCategory;
import com.project.product.domain.ProductOrder;
import com.project.product.dto.request.ProductRequest;
import com.project.product.dto.response.ProductResponse;
import com.project.product.service.ProductService;
import com.querydsl.core.types.Order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductRequest productRequest) {
		Long id = productService.createProduct(productRequest);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/{id}")
			.buildAndExpand(id).toUri();

		return ResponseEntity.created(uri)
			.build();
	}

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getProducts(
		@RequestParam(required = false) ProductCategory category,
		@RequestParam(required = false) ProductOrder productOrder,
		@RequestParam(required = false) Order order,
		@RequestParam(required = false) String search
	) {
		List<ProductResponse> responses = productService.getProducts(category, productOrder, order, search);
		return ResponseEntity.ok(responses);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
		ProductResponse response = productService.getProduct(productId);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{productId}")
	public ResponseEntity<Void> updateProduct(
		@PathVariable Long productId,
		@Valid @RequestBody ProductRequest productRequest
	) {
		Long id = productService.updateProduct(productId, productRequest);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/{id}")
			.buildAndExpand(id).toUri();

		return ResponseEntity.created(uri)
			.build();
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return ResponseEntity.noContent().build();
	}
}
