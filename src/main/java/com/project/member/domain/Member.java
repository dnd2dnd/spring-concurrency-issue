package com.project.member.domain;

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

	@Column
	private String email;

	@Embedded
	private MemberNickname nickname;

	@Column
	private MemberPassword password;

	private Member(String email, MemberNickname nickname, MemberPassword password) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}

	public static Member of(String email, MemberNickname nickname, MemberPassword password) {
		return new Member(
			email,
			nickname,
			password
		);
	}
}
