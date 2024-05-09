package com.project.order.domain;

import static com.project.order.PaymentConstant.*;

import jakarta.persistence.Column;

public class BackAccount {
	private static final int BACK_ACCOUNT_LENGTH_LOWER_BOUND_INCLUSIVE = 10;
	private static final int BACK_ACCOUNT_LENGTH_UPPER_BOUND_INCLUSIVE = 14;

	@Column(unique = true)
	private String BackAccount;

	private BackAccount(String backAccount) {
		this.BackAccount = backAccount;
	}

	public static BackAccount from(String backAccount) {
		validateLength(backAccount);
		return new BackAccount(backAccount);
	}

	private static void validateLength(String backAccount) {
		if (BACK_ACCOUNT_LENGTH_LOWER_BOUND_INCLUSIVE <= backAccount.length()
			&& backAccount.length() <= BACK_ACCOUNT_LENGTH_UPPER_BOUND_INCLUSIVE) {
			return;
		}
		throw new IllegalArgumentException(BANK_ACCOUNT_LENGTH_INVALID);
	}

}
