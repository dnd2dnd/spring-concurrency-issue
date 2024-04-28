package com.project.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.member.domain.Member;
import com.project.member.domain.MemberNickname;

public interface MemberRepository extends JpaRepository<Member, Long> {
	boolean existsByNickname(MemberNickname memberNickname);
}
