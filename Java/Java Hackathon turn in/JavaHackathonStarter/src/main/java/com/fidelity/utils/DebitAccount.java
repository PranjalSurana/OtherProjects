package com.fidelity.utils;

import java.math.BigDecimal;

public class DebitAccount extends Account {
	
	private BigDecimal fee;
	
	public DebitAccount(String accountNumber, BigDecimal balance, BigDecimal fee) {
		super(accountNumber, balance);
		this.fee = fee;
	}
	
	@Override
	public BigDecimal calculateCurrentBalance() {
		BigDecimal value = getBalance();
		if (value.compareTo(fee) >= 0) {
			value = value.subtract(fee);
		}
		return value;		
	}

}