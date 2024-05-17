package com.project.order.domain;

import com.project.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	Member member;

	@Enumerated(EnumType.STRING)
	CardCompany cardCompany;

	@Embedded
	private Cards cards;

	@Column
	private boolean defaultPayMethod;

	private Card(Member member, CardCompany cardCompany, Cards cards, boolean defaultPayMethod) {
		this.member = member;
		this.cardCompany = cardCompany;
		this.cards = cards;
		this.defaultPayMethod = defaultPayMethod;
	}

	public static Card of(Member member, CardCompany cardCompany, String cards, boolean defaultPayMethod) {
		return new Card(member, cardCompany, Cards.from(cards), defaultPayMethod);
	}

}
