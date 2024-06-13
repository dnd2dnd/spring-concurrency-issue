package com.project.coupon.dto.response;

import com.project.coupon.domain.Coupon;
import com.project.coupon.domain.UserCoupon;
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
	public static MemberCouponResponse from(UserCoupon userCoupon) {
		Coupon coupon = userCoupon.getCoupon();
		return new MemberCouponResponse(
			userCoupon.getId(),
			coupon.getId(),
			coupon.getProduct().getId(),
			coupon.getProduct().getName(),
			coupon.getDiscountPolicy(),
			coupon.getUsePeriod(),
			coupon.getMinProductPrice(),
			userCoupon.getStatus()
		);
	}
}
