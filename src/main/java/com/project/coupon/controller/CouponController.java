package com.project.coupon.controller;

import com.project.coupon.dto.request.CouponRequest;
import com.project.coupon.dto.request.IssueCouponRequest;
import com.project.coupon.dto.response.MemberCouponResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {
    private final CouponService couponService;

    @GetMapping
    public ResponseEntity<List<MemberCouponResponse>> getMemberCoupons(@RequestParam Long memberId) {
        List<MemberCouponResponse> responses = couponService.getMemberCoupons(memberId);
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
