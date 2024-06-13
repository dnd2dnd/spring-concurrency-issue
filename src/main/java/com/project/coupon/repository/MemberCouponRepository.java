package com.project.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.coupon.domain.UserCoupon;

public interface MemberCouponRepository extends JpaRepository<UserCoupon, Long> {
	default UserCoupon getById(Long memberCouponId) {
		return findById(memberCouponId).orElseThrow(
			() -> new IllegalArgumentException("Not Found ID : " + memberCouponId));
	}
}
