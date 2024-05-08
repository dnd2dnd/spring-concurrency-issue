package com.project.seller.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.auth.domain.BaseAuth;
import com.project.member.domain.Email;
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
 */
@Entity
@Getter
// @Inheritance(strategy = InheritanceType.JOINED)
// @DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller extends BaseAuth {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private TaxpayerIdentificationNum taxpayerIdentificationNum;

	@Column
	private String businessLocation;

	@Column
	private String companyName;

	@Column
	private boolean isWithdrawn;

	private Seller(Email email, Password password, String businessLocation, String companyName,
		TaxpayerIdentificationNum taxpayerIdentificationNum) {
		super(email, password);
		this.businessLocation = businessLocation;
		this.companyName = companyName;
		this.taxpayerIdentificationNum = taxpayerIdentificationNum;
	}

	public static Seller of(String email, String password, PasswordEncoder passwordEncoder,
		String businessLocation, String companyName, String taxpayerIdentificationNum) {
		return new Seller(
			Email.from(email),
			Password.of(password, passwordEncoder),
			businessLocation,
			companyName,
			TaxpayerIdentificationNum.from(taxpayerIdentificationNum)
		);
	}

	public Long getId() {
		return id;
	}
}
