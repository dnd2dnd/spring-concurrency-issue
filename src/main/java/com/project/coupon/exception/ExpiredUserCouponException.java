package com.project.coupon.exception;

import com.project.common.BusinessException;

public class ExpiredUserCouponException extends BusinessException {
    public ExpiredUserCouponException() {
        super("이미 만료된 쿠폰입니다.");
    }
}

