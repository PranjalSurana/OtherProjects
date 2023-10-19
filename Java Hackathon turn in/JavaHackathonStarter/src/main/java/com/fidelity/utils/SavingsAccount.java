package com.fidelity.utils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingsAccount extends Account {

	private BigDecimal INTEREST_RATE;
	private LocalDate startDate;
	
	public SavingsAccount(String accountNumber, BigDecimal balance, BigDecimal INTEREST_RATE, LocalDate startDate) {
		super(accountNumber, balance);
		this.INTEREST_RATE = INTEREST_RATE;  // BigDecimal(0.03);
		this.startDate = startDate;
	}
	
	@Override
	public BigDecimal calculateCurrentBalance() {
		BigDecimal value = getBalance();
		if (LocalDate.now().isAfter(startDate.plusYears(1))) {
			value = value.multiply(INTEREST_RATE);
		}
		BigDecimal balance = value;
		return balance;
	}
}