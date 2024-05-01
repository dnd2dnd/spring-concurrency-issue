package com.project.member.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
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
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String email;

	@Embedded
	private Nickname nickname;

	@Embedded
	private Password password;

	private Member(String email, Nickname nickname, Password password) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}

	public static Member of(String email, Nickname nickname, Password password) {
		return new Member(
			email,
			nickname,
			password
		);
	}

	public void checkPassword(String rawPassword, PasswordEncoder passwordEncoder) {
		password.checkPassword(rawPassword, passwordEncoder);
	}

}
