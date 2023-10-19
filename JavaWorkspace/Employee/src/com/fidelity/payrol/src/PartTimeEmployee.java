package com.fidelity.payrol.src;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PartTimeEmployee extends Employee {

	private BigDecimal hourlyRate;
	private int hoursForMonth;
	
	public PartTimeEmployee(String name, BigDecimal hourlyRate, int hoursForMonth, LocalDate hireDate) {
		super(name, hireDate);
		this.hourlyRate = hourlyRate;
		this.hoursForMonth = hoursForMonth;
	}

	@Override
	public BigDecimal calculateMonthlyPay() {
		return new BigDecimal(hoursForMonth).multiply(hourlyRate);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = hourlyRate.longValue();
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (hourlyRate.compareTo(other.hourlyRate) != 0)
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
