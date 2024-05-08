package com.project.auth.service;

import com.project.member.dto.request.SignInRequest;
import com.project.member.dto.response.TokenResponse;

public interface LoginService {
	TokenResponse signIn(SignInRequest signInRequest);
}
