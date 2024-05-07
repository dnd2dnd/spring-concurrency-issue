package com.project.seller.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.member.domain.Email;
import com.project.member.domain.Nickname;
import com.project.member.domain.Password;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * - 판매자 아이디, Long , NN, PK
 * - 이메일 , Stirng , NN, UK
 * - 비밀번호 ,String , NN
 * - 닉네임, String , NN
 * - 사업자 번호, Stirng , NN, UK
 * - 업체명 ,String , NN
 * - 업체주소, String , NN
 * - 탈퇴 여부
 * */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private Email email;

	@Embedded
	private Password password;
	@Embedded
	private TaxpayerIdentificationNum taxpayerIdentificationNum;

	@Column
	private String BusinessLocation;

	@Column
	private String CompanyName;

	@Column
	private boolean isWithdrawn;

	private Seller(Email email, Password password,String businessLocation,String companyName,
		TaxpayerIdentificationNum taxpayerIdentificationNum) {
		this.email = email;
		this.password = password;
		this.BusinessLocation=businessLocation;
		this.CompanyName=companyName;
		this.taxpayerIdentificationNum=taxpayerIdentificationNum;
	}

	public static Seller of(String email, String password, PasswordEncoder passwordEncoder,
		String businessLocation,String companyName,String taxpayerIdentificationNum ) {
		return new Seller(
			Email.from(email),
			Password.of(password, passwordEncoder),
			businessLocation,
			companyName,
			TaxpayerIdentificationNum.from(taxpayerIdentificationNum)
		);
	}
	//TODO 일반 회원과 겹치는 로직이므로 공통 로직으로 빼야 함
	public void checkPassword(String rawPassword, PasswordEncoder passwordEncoder) {
		password.checkPassword(rawPassword, passwordEncoder);
	}

}
