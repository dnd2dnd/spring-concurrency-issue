package com.project.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.product.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

	default Product getById(Long productId) {
		return findById(productId).orElseThrow(
			() -> new IllegalArgumentException("Not Found ID : " + productId));
	}

}
