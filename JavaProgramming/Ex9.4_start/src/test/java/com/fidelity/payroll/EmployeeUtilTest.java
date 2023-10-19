package com.fidelity.payroll;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeUtilTest {

	private List<Employee> emps;

	@BeforeEach
	void setUp() {
		emps = new ArrayList<>();
		emps.add(new FullTimeEmployee("John Smith", LocalDate.of(2018, 8, 18), new BigDecimal("10000.0")));
		emps.add(new PartTimeEmployee("Captain Jack", LocalDate.of(2019, 1, 1), new BigDecimal("10.0"), 100));
		emps.add(new Consultant("Rose Tyler", LocalDate.of(2018, 7, 11), new BigDecimal("8000.0"), 10));
	}

	@Test
	void testTotalSalary1() {
		assertEquals(new BigDecimal("2633.33"), EmployeeUtil.calcTotalMonthlyPay(emps));
	}

	@Test
	void testTotalSalary2() {
		emps.add(new FullTimeEmployee("Amy Pond", LocalDate.of(2019, 1, 20), new BigDecimal("12000.0")));
		assertEquals(new BigDecimal("3633.33"), EmployeeUtil.calcTotalMonthlyPay(emps));
	}

	// Optional exercise to calculate total pay to date
	@Test
	void testTotalToDate1() {
		assertEquals(new BigDecimal("7333.32"), EmployeeUtil.calcTotalPayToDate(emps, LocalDate.of(2019, 1, 1)));
	}

	@Test
	void testTotalToDate2() {
		assertEquals(new BigDecimal("800.00"), EmployeeUtil.calcTotalPayToDate(emps, LocalDate.of(2018, 8, 11)));
	}

	// below negative tests to make sure arrays are valid and in bounds
	@Test
	void testTotalToDateNull() {
		assertEquals(new BigDecimal("0.00"), EmployeeUtil.calcTotalPayToDate(null, LocalDate.of(2018, 8, 11)));
	}

	@Test
	void testTotalSalaryNull() {
		assertEquals(new BigDecimal("0.00"), EmployeeUtil.calcTotalMonthlyPay(null));
	}

}
