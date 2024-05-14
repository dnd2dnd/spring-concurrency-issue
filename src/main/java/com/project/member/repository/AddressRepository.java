package com.project.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.member.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
	Address findByMemberIdAndDefaultAddress(Long memberId, boolean defaultAddress);

	default Address getById(Long addressId) {
		return findById(addressId).orElseThrow(
			() -> new IllegalArgumentException("Not Found ID : " + addressId));
	}
}
