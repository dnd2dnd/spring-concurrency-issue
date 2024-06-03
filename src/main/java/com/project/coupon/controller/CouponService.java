package com.project.coupon.controller;

import com.project.coupon.domain.Coupon;
import com.project.coupon.domain.MemberCoupon;
import com.project.coupon.dto.request.CouponRequest;
import com.project.coupon.dto.request.IssueCouponRequest;
import com.project.coupon.dto.response.MemberCouponResponse;
import com.project.coupon.repository.CouponRepository;
import com.project.coupon.repository.MemberCouponRepository;
import com.project.member.domain.Member;
import com.project.member.repository.MemberRepository;
import com.project.product.domain.Price;
import com.project.product.domain.Product;
import com.project.product.domain.Stock;
import com.project.product.repository.ProductRepository;
import com.project.seller.domain.Seller;
import com.project.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {
    private final MemberRepository memberRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    public List<MemberCouponResponse> getMemberCoupons(Long memberId) {
        Member member = memberRepository.getById(memberId);

        return member.getMemberCoupons()
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
        Member member = memberRepository.getById(issueCouponRequest.memberId());

        MemberCoupon memberCoupon = MemberCoupon.of(member, coupon);
        memberCouponRepository.save(memberCoupon);
    }

}
