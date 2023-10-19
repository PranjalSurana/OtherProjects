package com.fidelity.payroll;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * These tests are a bit of a nonsense since it makes no sense to create an Employee
 * that is not a PartTimeEmployee nor a FullTimeEmployee. However, we haven't yet
 * addressed that particular detail, so for now we need these tests.
 */
class EmployeeTest {
	
	private Employee e;
	private BigDecimal zeroTwoDps;
	
	@BeforeEach
	void setUp() {
        e = new Employee("Jane Doe", LocalDate.of(2019, 1, 1));
        zeroTwoDps = new BigDecimal("0.00");
	}

	@Test
	void testCalculateMonthlyPay() {
        assertEquals(zeroTwoDps, e.calculateMonthlyPay());
	}
	
	@Test
	void testCalculateMonthlyPayBonus1() {
        assertEquals(zeroTwoDps, e.calculateMonthlyPay(BigDecimal.ZERO));
	}
	
	@Test
	void testCalculateMonthlyPayBonus2() {
        assertEquals(new BigDecimal("10.00"), e.calculateMonthlyPay(new BigDecimal("10.0")));
	}
	
	@Test
	void testCalculateMonthlyPayBonus3() {
        assertEquals(new BigDecimal("10.00"), e.calculateMonthlyPay(new BigDecimal("10.000")));
	}

	// Cannot test "pay to date" here because the result will always be zero, test in subclasses
}
