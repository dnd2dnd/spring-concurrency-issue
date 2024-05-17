package com.project.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.order.domain.Card;

public interface CardRepository extends JpaRepository<Card, Long> {
	Card findByMember_IdAndDefaultPayMethod(Long memberId, boolean defaultPayMethod);

	default Card getById(Long cardId) {
		return findById(cardId).orElseThrow(
			() -> new IllegalArgumentException("Not Found ID : " + cardId));
	}
}
