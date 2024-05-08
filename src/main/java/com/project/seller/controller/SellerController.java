package com.project.seller.domain;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.member.dto.request.SignInRequest;
import com.project.member.dto.response.TokenResponse;
import com.project.seller.dto.request.SellerSignUpReuqest;
import com.project.seller.service.SellerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register-seller")
public class SellerController {
	private final SellerService sellerService;
	@PostMapping("sign-up")
	public ResponseEntity<Void> signUp(@Valid @RequestBody SellerSignUpReuqest signUpRequest) {
		Long id = sellerService.SellerSignUp(signUpRequest);
		URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/{id}")
			.buildAndExpand(id).toUri();
		return ResponseEntity.created(uri)
			.build();
	}

	@PostMapping("sign-in")
	public ResponseEntity<TokenResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
		TokenResponse response = sellerService.signIn(signInRequest);
		return ResponseEntity.ok(response);
	}
}
