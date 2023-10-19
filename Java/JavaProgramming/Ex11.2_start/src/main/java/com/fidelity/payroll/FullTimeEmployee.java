package com.fidelity.payroll;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FullTimeEmployee extends Employee {

	private BigDecimal salary;

	public FullTimeEmployee(String name, LocalDate hireDate, BigDecimal salary) {
		super(name, hireDate);
		this.salary = salary.setScale(2);
	}

	@Override
	public BigDecimal calculateMonthlyPay() {
		return salary.divide(new BigDecimal("12.00"), 2, java.math.RoundingMode.HALF_UP);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
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
		FullTimeEmployee other = (FullTimeEmployee) obj;
		if (salary == null) {
			if (other.salary != null)
				return false;
		} else if (!salary.equals(other.salary))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FullTimeEmployee [salary=" + salary + ", toString()=" + super.toString() + "]";
	}
	
	
}
