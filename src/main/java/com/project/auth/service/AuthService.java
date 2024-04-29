package com.project.auth.service;

import static com.project.member.MemberConstant.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.auth.JWTProvider;
import com.project.common.BusinessException;
import com.project.member.domain.Member;
import com.project.member.domain.MemberNickname;
import com.project.member.domain.MemberPassword;
import com.project.member.dto.request.SignInRequest;
import com.project.member.dto.request.SignUpRequest;
import com.project.member.dto.response.TokenResponse;
import com.project.member.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
	private final PasswordEncoder passwordEncoder;
	private final JWTProvider jwtProvider;
	private final MemberRepository memberRepository;

	public Long signUp(SignUpRequest signUpRequest) {
		Member member = saveMember(signUpRequest);
		return member.getId();
	}

	public Member saveMember(SignUpRequest signUpRequest) {
		validateDuplicatedNickName(signUpRequest.nickname());
		Member member = toMember(signUpRequest);
		return memberRepository.save(member);
	}

	public Member toMember(SignUpRequest signUpRequest) {
		return Member.of(
			signUpRequest.email(),
			MemberNickname.from(signUpRequest.nickname()),
			MemberPassword.of(signUpRequest.password(), passwordEncoder)
		);
	}

	public void validateDuplicatedNickName(String nickname) {
		if (memberRepository.existsByNickname(MemberNickname.from(nickname))) {
			throw new BusinessException(NICKNAME_DUPLICATED);
		}
	}

	public TokenResponse signIn(SignInRequest signInRequest) {
		Member member = memberRepository.findByEmail(signInRequest.email())
			.orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, EMAIL_NOT_FOUND));

		member.validPassword(signInRequest.password(), passwordEncoder);

		return jwtProvider.createAccessToken(member.getId());
	}
}
