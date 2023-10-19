package com.fidelity.payrol.src;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public abstract class Employee {

	private String name;
	private LocalDate hireDate;

	public Employee(String name, LocalDate hireDate) {
		super();
		this.name = name;
		this.hireDate = hireDate;
	}
	
	public BigDecimal calculateMonthlyPay() {
		return new BigDecimal(0.0);
	}
	
	public BigDecimal calculateMonthlyPay(BigDecimal bonus) {
		return calculateMonthlyPay().add(bonus);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + "]";
	}
	
	public BigDecimal calculatePayToDate(LocalDate date) {
		LocalDate currentDate = LocalDate.now();
		if(date == null || date.isAfter(currentDate))
			return new BigDecimal(0.0);
		long daysDifference = ChronoUnit.DAYS.between(date, currentDate);
//		Period daysDifference = Period.between(date, currentDate);
		int averageDaysInMonth = 30;
//		long numberOfMonths = daysDifference / averageDaysInMonth;
		Period period = Period.between(date, currentDate);
		int numberOfMonths = period.getYears() * 12 + period.getMonths();
		System.out.println(numberOfMonths);
		return new BigDecimal(numberOfMonths).multiply(calculateMonthlyPay());
	}

}