package com.fidelity.payrol.test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.fidelity.payrol.src.Employee;
import com.fidelity.payrol.src.FullTimeEmployee;

class FullTimeEmployeeTest {

	@Test
	void testCalculateMonthlyPay1() {
		Employee ft = new FullTimeEmployee("Jane Doe", new BigDecimal(12000.0),LocalDate.parse("2018-11-01"));
		int comparisonResult = new BigDecimal(1000.0).compareTo(ft.calculateMonthlyPay());
        assertEquals(0, comparisonResult, 0.01);
	}

	@Test
	void testCalculateMonthlyPay2() {
		Employee ft = new FullTimeEmployee("Jane Doe", new BigDecimal("10000.0"), LocalDate.parse("2018-11-01"));
		assertEquals(new BigDecimal("833.33"), ft.calculateMonthlyPay());
	}


	@Test
	void testCalculateMonthlyPayBonus() {
		Employee ft = new FullTimeEmployee("Jane Doe", new BigDecimal("10000.0"), LocalDate.parse("2018-11-01"));
		assertEquals(new BigDecimal("843.33"), ft.calculateMonthlyPay(new BigDecimal("10.00")));
	}

    @Test
    void testFTEmployeeEquality() {
		Employee emp = new FullTimeEmployee("John Doe", new BigDecimal(1000.0), LocalDate.parse("2018-11-01"));
    	assertEquals(new FullTimeEmployee("John Doe", new BigDecimal(1000.0), LocalDate.parse("2018-11-01")), emp);
    	assertNotEquals(new FullTimeEmployee("John D", new BigDecimal(2000.0), LocalDate.parse("2018-11-01")),emp);
    }
    
    @Test
	void testCalculatePayTillDate() {
		Employee ft = new FullTimeEmployee("Jane Doe", new BigDecimal(12000.0), LocalDate.parse("2018-11-01"));
		int comparisonResult = new BigDecimal(58000.0).compareTo(ft.calculatePayToDate(LocalDate.parse("2018-11-01")));
        assertEquals(0, comparisonResult, 0.01);
	}
    
    @Test
	void testCalculatePayTillDateNullDate() {
		Employee ft = new FullTimeEmployee("Jane Doe", new BigDecimal(12000.0), null);
		int comparisonResult = new BigDecimal(0.0).compareTo(ft.calculatePayToDate(null));
        assertEquals(0.0, comparisonResult, 0.01);
	}
    
    @Test
	void testCalculatePayTillDateEarlierStartDate() {
		Employee ft = new FullTimeEmployee("Jane Doe", new BigDecimal(12000.0), LocalDate.parse("2024-11-01"));
		int comparisonResult = new BigDecimal(0.0).compareTo(ft.calculatePayToDate(LocalDate.parse("2024-11-01")));
        assertEquals(0.0, comparisonResult, 0.01);
	}

}
