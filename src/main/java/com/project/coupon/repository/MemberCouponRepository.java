package com.project.coupon.repository;

import com.project.coupon.domain.MemberCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {
    default MemberCoupon getById(Long memberCouponId) {
        return findById(memberCouponId).orElseThrow(
            () -> new IllegalArgumentException("Not Found ID : " + memberCouponId));
    }
}
