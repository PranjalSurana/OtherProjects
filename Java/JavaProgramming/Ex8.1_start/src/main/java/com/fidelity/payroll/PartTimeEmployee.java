package com.fidelity.payroll;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PartTimeEmployee extends Employee {

	private BigDecimal hourlyRate;
	private int hoursForMonth;
	
	public PartTimeEmployee(String name, LocalDate hireDate, BigDecimal hourlyRate, int hoursForMonth) {
		super(name, hireDate);
		this.hourlyRate = hourlyRate.setScale(2);
		this.hoursForMonth = hoursForMonth;
	}

	@Override
	public BigDecimal calculateMonthlyPay() {
		return hourlyRate.multiply(new BigDecimal(hoursForMonth)).setScale(2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((hourlyRate == null) ? 0 : hourlyRate.hashCode());
		result = prime * result + hoursForMonth;
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
		PartTimeEmployee other = (PartTimeEmployee) obj;
		if (hourlyRate == null) {
			if (other.hourlyRate != null)
				return false;
		} else if (!hourlyRate.equals(other.hourlyRate))
			return false;
		if (hoursForMonth != other.hoursForMonth)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PartTimeEmployee [hourlyRate=" + hourlyRate + ", hoursForMonth=" + hoursForMonth + ", toString()="
				+ super.toString() + "]";
	}
	
}
