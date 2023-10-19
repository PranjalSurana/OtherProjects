package com.fidelity.payrol.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.payrol.src.Employee;

class EmployeeTest {
	
	private Employee e;
	
	@BeforeEach
	void setUp() {
        e = new Employee("Jane Doe", LocalDate.parse("2018-11-01"));
	}

	@Test
	void testCalculateMonthlyPay() {
		int comparisonResult = new BigDecimal(0.0).compareTo(e.calculateMonthlyPay());
        assertEquals(0, comparisonResult, 0.001);
	}
	
	@Test
	void testCalculateMonthlyPayBonus1() {
		int comparisonResult = new BigDecimal(0.0).compareTo(e.calculateMonthlyPay(new BigDecimal(0.0)));
        assertEquals(0, comparisonResult, 0.001);
	}
	
	@Test
	void testCalculateMonthlyPayBonus2() {
		int comparisonResult = new BigDecimal(10.0).compareTo(e.calculateMonthlyPay(new BigDecimal(10.0)));
        assertEquals(0, comparisonResult, 0.001);
	}
	
}