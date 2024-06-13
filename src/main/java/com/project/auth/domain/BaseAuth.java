package com.project.auth.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.common.BaseTime;
import com.project.user.domain.Email;
import com.project.user.domain.Password;

import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor
public abstract class BaseAuth extends BaseTime {
	@Embedded
	private Email email;

	@Embedded
	private Password password;

	public BaseAuth(Email email, Password password) {
		this.email = email;
		this.password = password;
	}

	public String getPassword() {
		return password.getPassword();
	}

	public String getEmail() {
		return email.getEmail();
	}

	public void checkPassword(String rawPassword, PasswordEncoder passwordEncoder) {
		password.checkPassword(rawPassword, passwordEncoder);
	}
}
