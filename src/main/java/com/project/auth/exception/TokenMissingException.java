package com.project.auth.exception;

import static org.springframework.http.HttpStatus.*;

import com.project.common.BusinessException;

public class TokenMissingException extends BusinessException {

    public TokenMissingException() {
        super(UNAUTHORIZED, "토큰이 누락되었습니다.");
    }
}
