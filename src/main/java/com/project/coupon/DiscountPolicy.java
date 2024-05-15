package com.project.coupon;

import com.project.product.domain.Price;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountPolicy {
	@Column
	private DiscountType discountType;

	@Column
	private Price discountPrice;

	@Column
	private Double discountPercent;

	private DiscountPolicy(DiscountType discountType, Price discountPrice, Double discountPercent) {
		this.discountType = discountType;
		this.discountPrice = discountPrice;
		this.discountPercent = discountPercent;
	}

	public static DiscountPolicy of(DiscountType discountType, Price discountPrice, Double discountPercent) {
		return new DiscountPolicy(discountType, discountPrice, discountPercent);
	}
}
