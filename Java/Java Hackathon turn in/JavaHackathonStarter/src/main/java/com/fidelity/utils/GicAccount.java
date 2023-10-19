package com.fidelity.utils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GicAccount extends Account {

	private BigDecimal INTEREST_RATE;
	private LocalDate startDate;
	private int termInMonths;
	private BigDecimal fee;
	
	public GicAccount(String accountNumber, BigDecimal balance, BigDecimal INTEREST_RATE, LocalDate startDate, int termInMonths, BigDecimal fee) {
		super(accountNumber, balance);
		this.INTEREST_RATE = INTEREST_RATE;  // BigDecimal(0.05);
		this.startDate = startDate;
		this.termInMonths = termInMonths;
		this.fee = fee;
	}
	
	@Override
	public BigDecimal calculateCurrentBalance() {
		BigDecimal value = getBalance();
		if (LocalDate.now().isAfter(startDate.plusMonths(termInMonths))) {
			value = value.multiply(INTEREST_RATE);
		}
		if (value.compareTo(fee) >= 0) {
			value = value.subtract(fee);
		}
		return value;
	}
	
}