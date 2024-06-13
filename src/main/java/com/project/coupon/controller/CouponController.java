package com.project.coupon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.coupon.dto.request.CouponRequest;
import com.project.coupon.dto.request.IssueCouponRequest;
import com.project.coupon.dto.response.MemberCouponResponse;
import com.project.coupon.service.CouponService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {
	private final CouponService couponService;

	@GetMapping
	public ResponseEntity<List<MemberCouponResponse>> getMemberCoupons(@RequestParam Long userId) {
		List<MemberCouponResponse> responses = couponService.getMemberCoupons(userId);
		return ResponseEntity.ok(responses);
	}

	@PostMapping
	public ResponseEntity<Void> createCoupon(
		@RequestBody CouponRequest couponRequest
	) {
		couponService.createCoupon(couponRequest);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/issue")
	public ResponseEntity<Void> issueCoupon(
		@Valid @RequestBody IssueCouponRequest issueCouponRequest
	) {
		couponService.issueCoupon(issueCouponRequest);
		return ResponseEntity.ok().build();
	}
}
