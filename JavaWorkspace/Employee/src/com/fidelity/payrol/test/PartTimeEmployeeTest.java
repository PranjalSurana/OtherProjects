package com.fidelity.payrol.test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.payrol.src.Employee;
import com.fidelity.payrol.src.FullTimeEmployee;
import com.fidelity.payrol.src.PartTimeEmployee;

class PartTimeEmployeeTest {
	
	private Employee pt;
	
	@BeforeEach
	void setUp() {
        pt = new PartTimeEmployee("Jane Doe", new BigDecimal(10), 100, LocalDate.parse("2018-11-01"));
	}

	@Test
	void testCalculateMonthlyPay1() {
		int comparisonResult = new BigDecimal(1000.00).compareTo(pt.calculateMonthlyPay());
        assertEquals(0, comparisonResult, 0.01);
	}
	
	@Test
	void testCalculateMonthlyPay2() {
        pt = new PartTimeEmployee("Jane Doe", new BigDecimal(20), 100, LocalDate.parse("2018-11-01"));
		int comparisonResult = new BigDecimal(2000.00).compareTo(pt.calculateMonthlyPay());
        assertEquals(0, comparisonResult, 0.01);
	}	
	
	@Test
	void testCalculateMonthlyPay3() {
        pt = new PartTimeEmployee("Jane Doe", new BigDecimal(20), 200, LocalDate.parse("2018-11-01"));
		int comparisonResult = new BigDecimal(4000.00).compareTo(pt.calculateMonthlyPay());
        assertEquals(0, comparisonResult, 0.01);
	}

	@Test
	void testCalculateMonthlyPayBonus1() {
		int comparisonResult = new BigDecimal(1000.00).compareTo(pt.calculateMonthlyPay(new BigDecimal(0.0)));
        assertEquals(0, comparisonResult, 0.01);
	}
	
	@Test
	void testCalculateMonthlyPayBonus2() {
		int comparisonResult = new BigDecimal(1010.00).compareTo(pt.calculateMonthlyPay(new BigDecimal(10.0)));
        assertEquals(0, comparisonResult, 0.01);
	}
	
	@Test
	void testPTEmployeeEqualityEquals() {
		Employee emp = new PartTimeEmployee("John Doe", new BigDecimal(10.0), 100, LocalDate.parse("2018-11-01"));
		boolean compare = new PartTimeEmployee("John Doe", new BigDecimal(10.0), 100, LocalDate.parse("2018-11-01")).equals(emp);
		assertTrue(compare);
	}
	
	@Test
	void testPTEmployeeEqualityNotEquals() {
		Employee emp = new PartTimeEmployee("John Doe", new BigDecimal(10.0), 100, LocalDate.parse("2018-11-01"));
		assertNotEquals(new PartTimeEmployee("John Doe", new BigDecimal(10.0), 200, LocalDate.parse("2018-11-01")), emp);
	}
	
	@Test
	void testPTCalculatePayTillDate() {
		Employee ft = new FullTimeEmployee("Jane Doe", new BigDecimal(12000.0), LocalDate.parse("2018-11-01"));
		int comparisonResult = new BigDecimal(58000.0).compareTo(pt.calculatePayToDate(LocalDate.parse("2018-11-01")));
        assertEquals(0, comparisonResult, 0.01);
	}
    
    @Test
	void testPTCalculatePayTillDateNullDate() {
    	PartTimeEmployee pt1 = new PartTimeEmployee("Jane Doe", new BigDecimal(10), 100, null);
		int comparisonResult = new BigDecimal(0.0).compareTo(pt1.calculatePayToDate(null));
        assertEquals(0.0, comparisonResult, 0.01);
	}
    
    @Test
	void testPTCalculatePayTillDateEarlierStartDate() {
    	PartTimeEmployee pt1 = new PartTimeEmployee("Jane Doe", new BigDecimal(10), 100, LocalDate.parse("2024-11-01"));
		int comparisonResult = new BigDecimal(0.0).compareTo(pt1.calculatePayToDate(LocalDate.parse("2024-11-01")));
        assertEquals(0.0, comparisonResult, 0.01);
	}

	
}