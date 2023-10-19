package com.fidelity.utils;

import java.math.BigDecimal;

public abstract class Account {

	private String accountNumber;
	private BigDecimal balance;
	
	public Account(String accountNumber, BigDecimal balance) {
		this.accountNumber = accountNumber;
		this.balance = balance;
	}
	
	public abstract BigDecimal calculateCurrentBalance();

	public String getAccountNumber() {
		return accountNumber;
	}
	
	public BigDecimal getBalance() {
		return this.balance;
	}
	
}