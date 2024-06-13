package com.project.user.domain;

import com.project.common.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JoinColumn(name = "users_id")
	private User user;

	@Column(unique = true, nullable = false)
	String address;

	@Column
	private boolean defaultAddress;

	private Address(User user, String address, boolean defaultAddress) {
		this.user = user;
		this.address = address;
		this.defaultAddress = defaultAddress;
	}

	public static Address of(User user, String address, boolean defaultAddress) {
		return new Address(
			user,
			address,
			defaultAddress
		);
	}

	public void changeDefaultAddress(boolean defaultAddress) {
		this.defaultAddress = defaultAddress;
	}
}
