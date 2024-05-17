package com.project.member.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.auth.domain.BaseAuth;
import com.project.order.domain.Order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Order> orderList = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Address> adressList = new ArrayList<>();

	// @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	// private final List<Card> cardList = new ArrayList<>();

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

	// public void addCard(Card card) {
	// 	cardList.add(card);
	// 	card.setMember(this);
	// }

}
