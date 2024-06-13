package com.project.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.user.domain.Email;
import com.project.user.domain.Nickname;
import com.project.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByNickname(Nickname nickname);

	Optional<User> findByEmail(Email email);

	default User getById(Long memberId) {
		return findById(memberId).orElseThrow(
			() -> new IllegalArgumentException("Not Found ID : " + memberId));
	}
}
