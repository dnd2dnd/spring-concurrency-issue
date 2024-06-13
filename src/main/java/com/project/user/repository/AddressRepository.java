package com.project.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.user.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
	Optional<Address> findByUser_IdAndDefaultAddress(Long userId, boolean defaultAddress);

	default Address getById(Long addressId) {
		return findById(addressId).orElseThrow(
			() -> new IllegalArgumentException("Not Found ID : " + addressId));
	}
}
