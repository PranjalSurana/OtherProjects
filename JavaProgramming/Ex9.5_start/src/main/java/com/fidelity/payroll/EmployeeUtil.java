package com.fidelity.payroll;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class EmployeeUtil {

	private static final BigDecimal ZERO_TWO_DPS = BigDecimal.ZERO.setScale(2);

	public static BigDecimal calcTotalMonthlyPay(List<Employee> emps) {
		if (emps == null) {
			return ZERO_TWO_DPS;
		}

		BigDecimal total = ZERO_TWO_DPS;
		for (Employee emp : emps) {
			total = total.add(emp.calculateMonthlyPay());
		}
		return total;
	}

	public static BigDecimal calcTotalPayToDate(List<Employee> emps, LocalDate date) {
		if (emps == null) {
			return ZERO_TWO_DPS;
		}

		BigDecimal total = ZERO_TWO_DPS;
		for (Employee emp : emps) {
			total = total.add(emp.calculatePayToDate(date));
		}
		return total;
	}

	public static void sort(List<Employee> emps) {
		Collections.sort(emps);
	}

}
