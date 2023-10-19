package com.fidelity.payroll;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Objects;

public abstract class Employee implements Comparable<Employee> {

	private String name;
	private LocalDate hireDate;

	public Employee(String name, LocalDate hireDate) {
		super();
		setName(name);
		setHireDate(hireDate);
	}

	/*
	 *  Centralize exception handling in setters. Currently these are not needed, so they are marked
	 *  private, but they could be changed to public safely without invalidating exception code.
	 *  
	 *  All hierarchy classes throw NullPointerException for nulls, IllegalArgumentException otherwise.
	 */
	private void setName(String name) {
		Objects.requireNonNull(name, "Employee name cannot be null or empty");
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException("Employee name cannot be null or empty");
		}
		this.name = name;
	}

	private void setHireDate(LocalDate hireDate) {
		Objects.requireNonNull(hireDate, "Employee hire date cannot be null");
		this.hireDate = hireDate;
	}

	public abstract BigDecimal calculateMonthlyPay();
	
	public BigDecimal calculateMonthlyPay(BigDecimal bonus) {
		return calculateMonthlyPay().add(bonus).setScale(2);
	}

	public BigDecimal calculatePayToDate(LocalDate date) {
		Objects.requireNonNull(date, "Current date cannot be null");

		long months = Math.max(0, ChronoUnit.MONTHS.between(hireDate, date));
		return calculateMonthlyPay().multiply(new BigDecimal(months)).setScale(2); 
	}

	// Compare Employees by name
	@Override
	public int compareTo(Employee o) {
		return name.compareTo(o.name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hireDate == null) ? 0 : hireDate.hashCode());
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
		if (hireDate == null) {
			if (other.hireDate != null)
				return false;
		} else if (!hireDate.equals(other.hireDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", hireDate=" + hireDate + "]";
	}

	public String getName() {
		return name;
	}

	/*
	 * This is an example of a static inner class, which has access to the private members
	 * of the surrounding class. A good choice for a comparator and also works as a namespace
	 * to keep the comparator with the class it sorts.
	 */
	public static class PayComparator implements Comparator<Employee> {
		@Override
		public int compare(Employee o1, Employee o2) {
			// Note that this is a descending comparison on pay (the reverse a normal comparison)
			int payComp = o2.calculateMonthlyPay().compareTo(o1.calculateMonthlyPay());
			if (payComp == 0) {
				// Note that no getters are required as this is an inner class
				return o1.name.compareTo(o2.name);
			}
			return payComp;
		}
	}
}