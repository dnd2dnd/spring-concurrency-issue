package com.project.member.domain;

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
	@JoinColumn(name = "member_id")
	private Member member;

	@Column
	String address;

	@Column
	private boolean defaultAddress;

	private Address(String address, boolean defaultAddress) {
		this.address = address;
		this.defaultAddress = defaultAddress;
	}

	public static Address of(String address, boolean defaultAddress) {
		return new Address(
			address,
			defaultAddress
		);
	}
}
