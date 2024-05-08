package com.project.member.service;

import static com.project.member.MemberConstant.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.auth.JWTProvider;
import com.project.auth.service.LoginService;
import com.project.common.BusinessException;
import com.project.member.domain.Email;
import com.project.member.domain.Member;
import com.project.member.domain.Nickname;
import com.project.member.dto.request.SignInRequest;
import com.project.member.dto.request.SignUpRequest;
import com.project.member.dto.response.TokenResponse;
import com.project.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements LoginService {
	private final PasswordEncoder passwordEncoder;
	private final JWTProvider jwtProvider;
	private final MemberRepository memberRepository;

	@Override
	public TokenResponse signIn(SignInRequest signInRequest) {
		Member member = memberRepository.findByEmail(Email.from(signInRequest.email()))
			.orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, EMAIL_NOT_FOUND));

		member.checkPassword(signInRequest.password(), passwordEncoder);

		return jwtProvider.createAccessToken(member.getId());
	}

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
			signUpRequest.nickname(),
			signUpRequest.password(),
			passwordEncoder
		);
	}

	public void validateDuplicatedNickName(String nickname) {
		if (memberRepository.existsByNickname(Nickname.from(nickname))) {
			throw new BusinessException(NICKNAME_DUPLICATED);
		}
	}
}
