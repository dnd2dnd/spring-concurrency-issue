package com.project.coupon.dto.response;

import com.project.coupon.domain.Coupon;
import com.project.coupon.domain.MemberCoupon;
import com.project.coupon.domain.vo.DiscountPolicy;
import com.project.coupon.domain.vo.Status;
import com.project.coupon.domain.vo.UsePeriod;
import com.project.product.domain.Price;

public record MemberCouponResponse(
    Long memberCouponId,
    Long couponId,
    Long productId,
    String productName,
    DiscountPolicy discountPolicy,
    UsePeriod usePeriod,
    Price minProductPrice,
    Status status
) {
    public static MemberCouponResponse from(MemberCoupon memberCoupon) {
        Coupon coupon = memberCoupon.getCoupon();
        return new MemberCouponResponse(
            memberCoupon.getId(),
            coupon.getId(),
            coupon.getProduct().getId(),
            coupon.getProduct().getName(),
            coupon.getDiscountPolicy(),
            coupon.getUsePeriod(),
            coupon.getMinProductPrice(),
            memberCoupon.getStatus()
        );
    }
}
