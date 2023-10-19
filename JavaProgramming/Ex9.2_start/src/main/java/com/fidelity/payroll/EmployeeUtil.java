package com.fidelity.payroll;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeUtil {

	private static final BigDecimal ZERO_TWO_DPS = BigDecimal.ZERO.setScale(2);
	
	public static void sortByName(Employee[] emps) {
		Arrays.sort(emps, new EmployeeNameComparator());
	}

	public static void sortByMonthlyPay(Employee[] emps) {
		Arrays.sort(emps, new EmployeeMonthlyPayComparator());
	}
	
	public static BigDecimal totalMonthlyPayForAllEmployees(List<Employee> emplist) {
		if(emplist == null)
			return ZERO_TWO_DPS;
		BigDecimal totalMonthlyPay = ZERO_TWO_DPS;
		for (Employee emp: emplist)
			totalMonthlyPay = totalMonthlyPay.add(emp.calculateMonthlyPay());
		return totalMonthlyPay;
	}
	
	public static void sort(List<Employee> emps) {
		Collections.sort(emps);
	}
	
	public static void sort(List<Employee> emps, Comparator<Employee> comparator) {
		Collections.sort(emps, comparator);
	}

}