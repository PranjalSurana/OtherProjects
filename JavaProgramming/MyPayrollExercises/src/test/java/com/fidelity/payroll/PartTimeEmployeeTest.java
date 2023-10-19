package com.fidelity.payroll;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartTimeEmployeeTest {
	
	private Employee pt;
	
	@BeforeEach
	void setUp() {
        pt = new PartTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("10"), 100);
	}

	@Test
	void testCalculateMonthlyPay1() {
        assertEquals(new BigDecimal("1000.00"), pt.calculateMonthlyPay());
	}
	
	@Test
	void testCalculateMonthlyPay2() {
        pt = new PartTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("20"), 100);
        assertEquals(new BigDecimal("2000.00"), pt.calculateMonthlyPay());
	}	
	
	@Test
	void testCalculateMonthlyPay3() {
        pt = new PartTimeEmployee("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("20"), 200);
        assertEquals(new BigDecimal("4000.00"), pt.calculateMonthlyPay());
	}

	@Test
	void testCalculateMonthlyPayBonus1() {
        assertEquals(new BigDecimal("1000.00"), pt.calculateMonthlyPay(BigDecimal.ZERO));
	}
	
	@Test
	void testCalculateMonthlyPayBonus2() {
        assertEquals(new BigDecimal("1010.00"), pt.calculateMonthlyPay(new BigDecimal("10.000")));
	}
	
    @Test
    void testPTEmployeeEquality() {
    	Employee emp = new PartTimeEmployee("John Doe", LocalDate.of(2019, 1, 1), new BigDecimal("10.0"), 100);
    	assertEquals(new PartTimeEmployee("John Doe", LocalDate.of(2019, 1, 1), new BigDecimal("10.00"), 100), emp);
    	assertNotEquals(new PartTimeEmployee("John Doe", LocalDate.of(2019, 1, 1), new BigDecimal("10.00"), 200), emp);
    }

}
