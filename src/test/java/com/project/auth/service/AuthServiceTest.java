package com.project.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.datafaker.Faker;

import com.project.member.domain.Member;
import com.project.member.dto.request.SignUpRequest;
import com.project.member.repository.MemberRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class AuthServiceTest {

	private static final Faker faker = new Faker();
	private String validEmail;
	private String validNickname;
	private String validPassword;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthServicess authService;

	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	void setUp() {
		validEmail = faker.internet()
			.emailAddress();
		validNickname = "테스트";
		validPassword = "qTTT2344!@";
	}

	@Nested
	class 사용자_회원가입_테스트 {
		@Test
		void 회원가입에_성공한다() {
			// given
			SignUpRequest signUpRequest = new SignUpRequest(validEmail, validNickname, validPassword);

			// when
			Long savedMemberId = authService.signUp(signUpRequest);
			Member savedMember = memberRepository.getById(savedMemberId);

			// then
			assertAll(
				() -> assertThat(savedMember.getId()).isEqualTo(savedMemberId),
				() -> assertThat(savedMember.getEmail()).isEqualTo(validEmail),
				() -> assertThat(savedMember.getNickname()).isEqualTo(validNickname),
				() -> assertThat(
					passwordEncoder.matches(validPassword, savedMember.getPassword())).isTrue()
			);
		}

		@ParameterizedTest(name = "유효하지 않은 이메일 : {0}")
		@NullAndEmptySource
		@ValueSource(strings = {" ", "email", "email@example", "email@example.", "email@example.com."})
		void 유효하지_않은_이메일을_사용한_경우_회원가입을_실패한다(String email) {
			// given
			SignUpRequest signUpRequest = new SignUpRequest(email, validNickname, validPassword);

			// when
			ThrowingCallable throwingCallable = () -> authService.signUp(signUpRequest);

			// then
			assertThatExceptionOfType(InvalidParameterException.class).isThrownBy(throwingCallable);
		}

		@ParameterizedTest(name = "유효하지 않은 닉네임 : {0}")
		@NullAndEmptySource
		@ValueSource(strings = {" ", "곰", "!@#$", "닉네임@@.", "10글자가넘는닉네임테스트"})
		void 유효하지_않은_닉네임을_사용한_경우_회원가입을_실패한다(String nickname) {
			// given
			SignUpRequest signUpRequest = new SignUpRequest(validEmail, nickname, validPassword);

			// when
			ThrowingCallable throwingCallable = () -> authService.signUp(signUpRequest);

			// then
			assertThatExceptionOfType(InvalidParameterException.class).isThrownBy(throwingCallable);
		}

		@ParameterizedTest(name = "유효하지 않은 비밀번호 : {0}")
		@NullAndEmptySource
		@ValueSource(strings = {" ", "qwer", "qwer123@@@@", "qwerTEST789", "qwer****789"})
		void 유효하지_않은_비밀번호를_사용한_경우_회원가입을_실패한다(String email) {
			// given
			SignUpRequest signUpRequest = new SignUpRequest(email, validNickname, validPassword);

			// when
			ThrowingCallable throwingCallable = () -> authService.signUp(signUpRequest);

			// then
			assertThatExceptionOfType(InvalidParameterException.class).isThrownBy(throwingCallable);
		}
	}
}