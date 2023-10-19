package com.fidelity.payrol.src;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Consultant extends Employee {
	
	private BigDecimal contractAmount;
	private int contractLengthInMonths;
	
	public Consultant(String name, BigDecimal contractAmount, int contractLengthInMonths, LocalDate hireDate) {
		super(name, hireDate);
		this.contractAmount = contractAmount;
		this.contractLengthInMonths = contractLengthInMonths;
	}

	@Override
	public BigDecimal calculateMonthlyPay() {
		return contractAmount.divide(new BigDecimal(contractLengthInMonths), 2, RoundingMode.HALF_UP);
	}
	
	@Override
	public BigDecimal calculateMonthlyPay(BigDecimal bonus) {
		return calculateMonthlyPay().add(bonus);
	}

}