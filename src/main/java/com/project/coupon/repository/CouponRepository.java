package com.project.coupon.repository;

import com.project.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    default Coupon getById(Long couponId) {
        return findById(couponId).orElseThrow(
            () -> new IllegalArgumentException("Not Found ID : " + couponId));
    }
}
