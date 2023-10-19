package com.fidelity.payroll;

import java.util.Comparator;

public class EmployeeMonthlyPayComparator implements Comparator<Employee> {

	@Override
	public int compare(Employee employee1, Employee employee2) {
		return - employee1.calculateMonthlyPay().compareTo(employee2.calculateMonthlyPay());
	}

}
