package com.fidelity.payroll;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Consultant extends Employee {

	private BigDecimal contractAmount;
	private int contractLengthInMonths;

	public Consultant(String name, LocalDate hireDate, BigDecimal contractAmount, int contractLengthInMonths) {
		super(name, hireDate);
		this.contractAmount = contractAmount.setScale(2);
		this.contractLengthInMonths = contractLengthInMonths;
	}

	@Override
	public BigDecimal calculateMonthlyPay() {
		return contractAmount.divide(new BigDecimal(contractLengthInMonths), 2, RoundingMode.HALF_UP);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((contractAmount == null) ? 0 : contractAmount.hashCode());
		result = prime * result + contractLengthInMonths;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Consultant other = (Consultant) obj;
		if (contractAmount == null) {
			if (other.contractAmount != null)
				return false;
		} else if (!contractAmount.equals(other.contractAmount))
			return false;
		if (contractLengthInMonths != other.contractLengthInMonths)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Consultant [contractAmount=" + contractAmount + ", contractLengthInMonths=" + contractLengthInMonths
				+ ", toString()=" + super.toString() + "]";
	}

}
