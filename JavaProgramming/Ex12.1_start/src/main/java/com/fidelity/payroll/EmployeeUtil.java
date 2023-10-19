package com.fidelity.payroll;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.fidelity.payroll.services.PayrollService;

public class EmployeeUtil {

	private static PayrollService payrollService;

	public static BigDecimal calcTotalMonthlyPay(List<Employee> emps) {
		return payrollService.calcTotalMonthlyPay(emps);
	}

	public static BigDecimal calcTotalPayToDate(List<Employee> emps, LocalDate date) {
		return payrollService.calcTotalPayToDate(emps, date);
	}


	public static void sort(List<Employee> emps) {
		payrollService.sort(emps);
	}
	
	public static void sort(List<Employee> emps, Comparator<Employee> comparator) {
		payrollService.sort(emps, comparator);
	}

}
