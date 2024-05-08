package com.project.auth.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.project.common.BusinessException;

public class TokenExpiredException extends BusinessException {
    public TokenExpiredException() {
        super(UNAUTHORIZED, "만료된 토큰입니다. 올바른 토큰으로 다시 시도해주세요.");
    }
}
