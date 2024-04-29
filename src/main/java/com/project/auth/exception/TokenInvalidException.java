package com.project.auth.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.project.common.BusinessException;

public class TokenInvalidException extends BusinessException {

    public TokenInvalidException() {
        super(FORBIDDEN, "잘못된 토큰입니다. 올바른 토큰으로 다시 시도해주세요.");
    }
}
