package com.project.coupon.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.coupon.domain.Coupon;
import com.project.coupon.domain.UserCoupon;
import com.project.coupon.dto.request.CouponRequest;
import com.project.coupon.dto.request.IssueCouponRequest;
import com.project.coupon.dto.response.MemberCouponResponse;
import com.project.coupon.repository.CouponRepository;
import com.project.coupon.repository.MemberCouponRepository;
import com.project.product.domain.Price;
import com.project.product.domain.Product;
import com.project.product.domain.Stock;
import com.project.product.repository.ProductRepository;
import com.project.seller.domain.Seller;
import com.project.seller.repository.SellerRepository;
import com.project.user.domain.User;
import com.project.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {
	private final UserRepository userRepository;
	private final SellerRepository sellerRepository;
	private final ProductRepository productRepository;
	private final CouponRepository couponRepository;
	private final MemberCouponRepository memberCouponRepository;

	public List<MemberCouponResponse> getMemberCoupons(Long userId) {
		User user = userRepository.getById(userId);

		return user.getUserCoupons()
			.stream()
			.map(MemberCouponResponse::from)
			.toList();
	}

	@Transactional
	public void createCoupon(CouponRequest couponRequest) {
		Seller seller = sellerRepository.getById(couponRequest.sellerId());
		Product product = productRepository.getById(couponRequest.productId());
		Coupon coupon = Coupon.of(
			couponRequest.name(),
			couponRequest.discountPolicy(),
			couponRequest.usePeriod(),
			Stock.from(couponRequest.stock()),
			Price.from(couponRequest.minProductPrice()),
			product
		);
		couponRepository.save(coupon);
	}

	@Transactional
	public void issueCoupon(IssueCouponRequest issueCouponRequest) {
		Coupon coupon = couponRepository.getById(issueCouponRequest.couponId());
		coupon.getStock().validateStock();
		User user = userRepository.getById(issueCouponRequest.memberId());

		UserCoupon userCoupon = UserCoupon.of(user, coupon);
		memberCouponRepository.save(userCoupon);
	}

}
