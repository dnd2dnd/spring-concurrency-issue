package com.project.member.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.auth.domain.BaseAuth;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseAuth {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private Nickname nickname;

	private Member(Email email, Nickname nickname, Password password) {
		super(email, password);
		this.nickname = nickname;
	}

	public static Member of(String email, String nickname, String password, PasswordEncoder passwordEncoder) {
		return new Member(
			Email.from(email),
			Nickname.from(nickname),
			Password.of(password, passwordEncoder)
		);
	}

	public String getNickname() {
		return this.nickname.getNickname();
	}

}
