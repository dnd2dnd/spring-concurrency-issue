package com.project.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.member.domain.Email;
import com.project.member.domain.Member;
import com.project.member.domain.Nickname;

public interface MemberRepository extends JpaRepository<Member, Long> {
	boolean existsByNickname(Nickname nickname);

	Optional<Member> findByEmail(Email email);
	
	default Member getById(Long memberId) {
		return findById(memberId).orElseThrow(
			() -> new IllegalArgumentException("Not Found ID : " + memberId));
	}
}
