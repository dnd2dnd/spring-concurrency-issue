package com.project.seller.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.seller.domain.Seller;
import com.project.user.domain.Email;

public interface SellerRepository extends JpaRepository<Seller, Long> {
	Optional<Seller> findByEmail(Email email);

	default Seller getById(Long sellerId) {
		return findById(sellerId).orElseThrow(
			() -> new IllegalArgumentException("Not Found ID : " + sellerId));
	}
}
