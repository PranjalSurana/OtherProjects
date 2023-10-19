package com.fidelity.payroll.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.fidelity.payroll.Employee;

public class PayrollService {	

	private static final BigDecimal ZERO_TWO_DPS = BigDecimal.ZERO.setScale(2);
	
	public static BigDecimal calcTotalMonthlyPay(List<Employee> emps) {
		Objects.requireNonNull(emps, "Cannot calculate total pay for null list");

		BigDecimal total = ZERO_TWO_DPS;
		for (Employee emp : emps) {
			total = total.add(emp.calculateMonthlyPay());
		}
		return total;
	}

	public static BigDecimal calcTotalPayToDate(List<Employee> emps, LocalDate date) {
		Objects.requireNonNull(emps, "Cannot calculate total pay for null list");

		BigDecimal total = ZERO_TWO_DPS;
		for (Employee emp : emps) {
			total = total.add(emp.calculatePayToDate(date));
		}
		return total;
	}
	
	public static void sort(List<Employee> emps) {
		Collections.sort(emps);
	}

	public static void sort(List<Employee> emps, Comparator<Employee> comparator) {
		Collections.sort(emps, comparator);
	}

}