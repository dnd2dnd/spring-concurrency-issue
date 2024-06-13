package com.project.coupon.exception;

import com.project.common.BusinessException;

public class AlreadyUsedUserCouponException extends BusinessException {
    public AlreadyUsedUserCouponException() {
        super("이미 사용한 쿠폰입니다.");
    }
}
