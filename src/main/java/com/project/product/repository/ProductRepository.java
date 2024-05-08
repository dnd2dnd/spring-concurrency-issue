package com.project.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.product.domain.Product;
import com.project.seller.domain.Seller;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
	Product findByIdAndSeller(Long productId, Seller seller);

	default Product getById(Long productId) {
		return findById(productId).orElseThrow(
			() -> new IllegalArgumentException("Not Found ID : " + productId));
	}

}
