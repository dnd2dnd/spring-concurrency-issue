package com.project.coupon.dto.request;

import com.project.coupon.domain.vo.DiscountPolicy;
import com.project.coupon.domain.vo.UsePeriod;

public record CouponRequest(
    Long sellerId,
    Long productId,
    String name,
    DiscountPolicy discountPolicy,
    UsePeriod usePeriod,
    Integer stock,
    Integer minProductPrice
) {
}
