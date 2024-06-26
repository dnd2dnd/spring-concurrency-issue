package com.project.coupon.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    USED("사용됨"),
    UNUSED("사용되지 않음");

    private final String status;

}
