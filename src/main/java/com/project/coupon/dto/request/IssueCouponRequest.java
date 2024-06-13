package com.project.coupon.dto.request;

public record IssueCouponRequest(
    Long memberId,
    Long couponId
) {
}
