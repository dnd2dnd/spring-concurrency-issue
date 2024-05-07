package com.project.member.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.member.dto.request.SignInRequest;
import com.project.member.dto.request.SignUpRequest;
import com.project.member.dto.response.TokenResponse;
import com.project.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@PostMapping("sign-up")
	public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
		Long id = memberService.signUp(signUpRequest);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/{id}")
			.buildAndExpand(id).toUri();
		return ResponseEntity.created(uri)
			.build();
	}

	@PostMapping("sign-in")
	public ResponseEntity<TokenResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
		TokenResponse response = memberService.signIn(signInRequest);
		return ResponseEntity.ok(response);
	}
}
