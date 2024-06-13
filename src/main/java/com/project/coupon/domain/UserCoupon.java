package com.project.coupon.domain;

import java.time.LocalDateTime;

import com.project.coupon.domain.vo.Status;
import com.project.coupon.exception.AlreadyUsedUserCouponException;
import com.project.coupon.exception.ExpiredUserCouponException;
import com.project.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class UserCoupon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "coupon_id")
	private Coupon coupon;

	@Column(name = "used_at")
	private LocalDateTime usedAt;

	@Enumerated(EnumType.STRING)
	private Status status;

	private UserCoupon(User user, Coupon coupon, LocalDateTime usedAt, Status status) {
		this.user = user;
		this.coupon = coupon;
		this.usedAt = usedAt;
		this.status = status;
	}

	public static UserCoupon of(User user, Coupon coupon) {
		UserCoupon userCoupon = new UserCoupon(user, coupon, LocalDateTime.now(), Status.UNUSED);
		user.addCoupon(userCoupon);
		return userCoupon;
	}

	public void use(LocalDateTime currentTime) {
		validate(currentTime);
		status = Status.USED;
		usedAt = currentTime;
	}

	private void validate(LocalDateTime currentTime) {
		if (status.equals(Status.USED)) {
			throw new AlreadyUsedUserCouponException();
		}
		if (coupon.getEndAt().isBefore(currentTime)) {
			throw new ExpiredUserCouponException();
		}
	}
}
