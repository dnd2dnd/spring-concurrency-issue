package com.project.user.service;

import static com.project.user.UserConstant.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.auth.JWTProvider;
import com.project.auth.service.LoginService;
import com.project.common.BusinessException;
import com.project.user.domain.Email;
import com.project.user.domain.Nickname;
import com.project.user.domain.User;
import com.project.user.dto.request.SignInRequest;
import com.project.user.dto.request.SignUpRequest;
import com.project.user.dto.response.TokenResponse;
import com.project.user.repository.AddressRepository;
import com.project.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements LoginService {
	private final PasswordEncoder passwordEncoder;
	private final JWTProvider jwtProvider;
	private final UserRepository userRepository;
	private final AddressRepository addressRepository;

	// @PostConstruct
	// public void init() {
	// 	for (int i = 1; i <= 100; i++) {
	// 		String email = "user" + i + "@example.com";
	// 		String nickname = "user" + i;
	// 		String password = "P!assw!!" + i;
	// 		User user = User.of(email, nickname, password, passwordEncoder);
	// 		userRepository.save(user);
	// 	}
	// }

	// @PostConstruct
	// public void init() {
	// 	for (int i = 1; i <= 100; i++) {
	// 		User user = userRepository.getById((long)i);
	// 		String address = "user" + i;
	// 		boolean defaultAddress = true;
	// 		Address address1 = Address.of(user, address, defaultAddress);
	// 		addressRepository.save(address1);
	// 	}
	// }

	@Override
	public TokenResponse signIn(SignInRequest signInRequest) {
		User user = userRepository.findByEmail(Email.from(signInRequest.email()))
			.orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, EMAIL_NOT_FOUND));

		user.checkPassword(signInRequest.password(), passwordEncoder);

		return jwtProvider.createAccessToken(user.getId());
	}

	public Long signUp(SignUpRequest signUpRequest) {
		User user = saveMember(signUpRequest);
		return user.getId();
	}

	public User saveMember(SignUpRequest signUpRequest) {
		validateDuplicatedNickName(signUpRequest.nickname());
		User user = toMember(signUpRequest);
		return userRepository.save(user);
	}

	public User toMember(SignUpRequest signUpRequest) {
		return User.of(
			signUpRequest.email(),
			signUpRequest.nickname(),
			signUpRequest.password(),
			passwordEncoder
		);
	}

	public void validateDuplicatedNickName(String nickname) {
		if (userRepository.existsByNickname(Nickname.from(nickname))) {
			throw new BusinessException(NICKNAME_DUPLICATED);
		}
	}
}
