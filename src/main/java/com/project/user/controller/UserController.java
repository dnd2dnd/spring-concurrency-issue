package com.project.user.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.user.dto.request.SignInRequest;
import com.project.user.dto.request.SignUpRequest;
import com.project.user.dto.response.TokenResponse;
import com.project.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("sign-up")
	public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
		Long id = userService.signUp(signUpRequest);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/{id}")
			.buildAndExpand(id).toUri();
		return ResponseEntity.created(uri)
			.build();
	}

	@PostMapping("sign-in")
	public ResponseEntity<TokenResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
		TokenResponse response = userService.signIn(signInRequest);
		return ResponseEntity.ok(response);
	}
}
