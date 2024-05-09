package com.project.seller.service;

import static com.project.member.MemberConstant.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.auth.JWTProvider;
import com.project.auth.service.LoginService;
import com.project.common.BusinessException;
import com.project.member.domain.Email;
import com.project.member.dto.request.SignInRequest;
import com.project.member.dto.response.TokenResponse;
import com.project.seller.domain.Seller;
import com.project.seller.dto.request.SellerSignUpReuqest;
import com.project.seller.repository.SellerRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SellerService implements LoginService {
	private final PasswordEncoder passwordEncoder;
	private final JWTProvider jwtProvider;
	private final SellerRepository sellerRepository;

	@Override
	public TokenResponse signIn(SignInRequest signInRequest) {
		Seller seller = sellerRepository.findByEmail(Email.from(signInRequest.email()))
			.orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, EMAIL_NOT_FOUND));

		seller.checkPassword(signInRequest.password(), passwordEncoder);
		return jwtProvider.createAccessToken(seller.getId());
	}

	public Long SellerSignUp(SellerSignUpReuqest sellerSignUpReuqest) {
		Seller seller = saveSeller(sellerSignUpReuqest);
		return seller.getId();
	}

	public Seller saveSeller(SellerSignUpReuqest signUpRequest) {
		Seller seller = toSeller(signUpRequest);
		return sellerRepository.save(seller);
	}

	public Seller toSeller(SellerSignUpReuqest signUpRequest) {
		return Seller.of(
			signUpRequest.email(),
			signUpRequest.password(),
			passwordEncoder,
			signUpRequest.BusinessLocation(),
			signUpRequest.CompanyName(),
			signUpRequest.TaxpayerIdentificationNum()
		);
	}

}
