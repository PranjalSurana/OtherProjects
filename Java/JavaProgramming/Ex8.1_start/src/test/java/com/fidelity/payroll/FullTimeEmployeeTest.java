package com.fidelity.payroll;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FullTimeEmployeeTest {

	private BigDecimal zeroTwoDps;
	
	@BeforeEach
	void setUp() {
        zeroTwoDps = new BigDecimal("0.00");
	}

	@Test
	void testCalculateMonthlyPay1() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("12000.0"));
		assertEquals(new BigDecimal("1000.00"), ft.calculateMonthlyPay());
	}

	@Test
	void testCalculateMonthlyPay2() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("10000.0"));
		assertEquals(new BigDecimal("833.33"), ft.calculateMonthlyPay());
	}

	/*
	 * Note that this test was not developed TDD. The superclass already provided this
	 * functionality. However this is a "characteristic" test added to describe the
	 * expected operation of the class's API. It is a type of regression test to protect
	 * against inadvertent changes in the future.
	 */
	@Test
	void testCalculateMonthlyPayBonus() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("10000.0"));
		assertEquals(new BigDecimal("843.33"), ft.calculateMonthlyPay(new BigDecimal("10.000")));
	}

    @Test
    void testFTEmployeeEquality() {
    	Employee emp = new FullTimeEmployee("John Doe", LocalDate.of(2019, 1, 1), new BigDecimal("1000.0"));
    	assertEquals(new FullTimeEmployee("John Doe", LocalDate.of(2019, 1, 1), new BigDecimal("1000.00")), emp);
    	assertNotEquals(new FullTimeEmployee("John Doe", LocalDate.of(2019, 1, 1), new BigDecimal("2000.00")),emp);
    }
	
    // Test functionality of superclass. Cannot test there because monthly pay is always zero.
    @Test
	void testCalculatePayToDate1() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("12000.0"));
		assertEquals(new BigDecimal("6000.00"), ft.calculatePayToDate(LocalDate.of(2019, 7, 11)));
	}

    @Test
	void testCalculatePayToDate2() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("12000.0"));
		assertEquals(new BigDecimal("7000.00"), ft.calculatePayToDate(LocalDate.of(2019, 8, 18)));
	}

    // negative test when current date is older than start date
    @Test
	void testCalculatePayToDateBeforeHireDate() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("12000.0"));
		assertEquals(zeroTwoDps, ft.calculatePayToDate(LocalDate.of(2018, 8, 18)));
	}
    
    // negative test when current date is null
    @Test
	void testCalculatePayToDateWithNullDate() {
		Employee ft = new FullTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("12000.0"));
		assertEquals(zeroTwoDps, ft.calculatePayToDate(null));
	}

}
