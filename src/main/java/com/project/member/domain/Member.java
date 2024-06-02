package com.project.member.domain;

import com.project.auth.domain.BaseAuth;
import com.project.coupon.domain.MemberCoupon;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseAuth {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private Nickname nickname;

    @OneToMany(mappedBy = "member")
    private final List<MemberCoupon> memberCoupons = new ArrayList<>();

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

    public void addCoupon(MemberCoupon memberCoupon) {
        memberCoupons.add(memberCoupon);
    }

}
