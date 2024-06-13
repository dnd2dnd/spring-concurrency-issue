package com.project.user.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.auth.domain.BaseAuth;
import com.project.coupon.domain.UserCoupon;
import com.project.order.domain.Order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseAuth {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private Nickname nickname;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Order> orderList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Address> adressList = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private final List<UserCoupon> userCoupons = new ArrayList<>();

	private User(Email email, Nickname nickname, Password password) {
		super(email, password);
		this.nickname = nickname;
	}

	public static User of(String email, String nickname, String password, PasswordEncoder passwordEncoder) {
		return new User(
			Email.from(email),
			Nickname.from(nickname),
			Password.of(password, passwordEncoder)
		);
	}

	public String getNickname() {
		return this.nickname.getNickname();
	}

	public void addCoupon(UserCoupon userCoupon) {
		userCoupons.add(userCoupon);
	}

}
