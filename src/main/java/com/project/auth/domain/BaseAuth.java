package com.project.auth.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.member.domain.Email;
import com.project.member.domain.Password;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseAuth {
	@Embedded
	private Email email;
	@Embedded
	private Password password;

	public String getPassword() {
		return password.getPassword();
	}
	public String getEmail() {
		return this.email.getEmail();
	}

	public void checkPassword(String rawPassword, PasswordEncoder passwordEncoder) {
		password.checkPassword(rawPassword, passwordEncoder);
	}
}
