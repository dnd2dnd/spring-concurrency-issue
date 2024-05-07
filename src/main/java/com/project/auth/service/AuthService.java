package com.project.auth.service;

import static com.project.member.MemberConstant.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.auth.JWTProvider;
import com.project.common.BusinessException;
import com.project.member.domain.Email;
import com.project.member.domain.Member;
import com.project.member.domain.Nickname;
import com.project.member.dto.request.SignInRequest;
import com.project.member.dto.request.SignUpRequest;
import com.project.member.dto.response.TokenResponse;
import com.project.member.repository.MemberRepository;
import com.project.seller.domain.Seller;
import com.project.seller.dto.request.SellerSignUpReuqest;
import com.project.seller.repository.SellerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	private final PasswordEncoder passwordEncoder;
	private final JWTProvider jwtProvider;
	private final MemberRepository memberRepository;
	private final SellerRepository sellerRepository;

	public Long signUp(SignUpRequest signUpRequest) {
		Member member = saveMember(signUpRequest);
		return member.getId();
	}
	public Long SellerSignUp(SellerSignUpReuqest sellerSignUpReuqest) {
		Seller seller = saveSeller(sellerSignUpReuqest);
		return seller.getId();
	}

	public Member saveMember(SignUpRequest signUpRequest) {
		validateDuplicatedNickName(signUpRequest.nickname());
		Member member = toMember(signUpRequest);
		return memberRepository.save(member);
	}

	public Seller saveSeller(SellerSignUpReuqest signUpRequest) {
		Seller seller = toSeller(signUpRequest);
		return sellerRepository.save(seller);
	}
	public Member toMember(SignUpRequest signUpRequest) {
		return Member.of(
			signUpRequest.email(),
			signUpRequest.nickname(),
			signUpRequest.password(),
			passwordEncoder
		);
	}
	public Seller toSeller(SellerSignUpReuqest signUpRequest) {
		return Seller.of(
			signUpRequest.email(),
			signUpRequest.password(),
			passwordEncoder,
			signUpRequest.BuisinessLocation(),
			signUpRequest.CompanyName(),
			signUpRequest.TaxpayerIdentificationNum()
		);
	}

	public void validateDuplicatedNickName(String nickname) {
		if (memberRepository.existsByNickname(Nickname.from(nickname))) {
			throw new BusinessException(NICKNAME_DUPLICATED);
		}
	}

	public TokenResponse signIn(SignInRequest signInRequest) {
		Member member = memberRepository.findByEmail(Email.from(signInRequest.email()))
			.orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, EMAIL_NOT_FOUND));

		member.checkPassword(signInRequest.password(), passwordEncoder);

		return jwtProvider.createAccessToken(member.getId());
	}
	//TODO entity만 다르고 같은 로직이라 뭔가 코드 중복인 느낌이네요.. 어떻게 방법이 없을까요!?
	public TokenResponse SellerSignIn(SignInRequest signInRequest) {
		Seller seller = sellerRepository.findByEmail(Email.from(signInRequest.email()))
			.orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, EMAIL_NOT_FOUND));

		seller.checkPassword(signInRequest.password(), passwordEncoder);

		return jwtProvider.createAccessToken(seller.getId());
	}
}
