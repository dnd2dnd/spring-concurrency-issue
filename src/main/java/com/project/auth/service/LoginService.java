package com.project.auth.service;

import com.project.user.dto.request.SignInRequest;
import com.project.user.dto.response.TokenResponse;

public interface LoginService {
	TokenResponse signIn(SignInRequest signInRequest);
}
