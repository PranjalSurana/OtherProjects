package com.fidelity.payroll;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ConsultantTest {

	@Test
	void testCalculateMonthlyPay1() {
		Employee c = new Consultant("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("12000.0"), 12);
		assertEquals(new BigDecimal("1000.00"), c.calculateMonthlyPay());
	}

	@Test
	void testCalculateMonthlyPay2() {
		Employee c = new Consultant("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("8000.0"), 10);
		assertEquals(new BigDecimal("800.00"), c.calculateMonthlyPay());
	}

	@Test
	void testCalculateMonthlyPayBonus() {
		Employee c = new Consultant("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("8000.0"), 10);
		assertEquals(new BigDecimal("842.00"), c.calculateMonthlyPay(new BigDecimal("42.0")));
	}

    @Test
    void testConsultantEquality() {
    	Employee emp = new Consultant("John Doe", LocalDate.of(2019, 1, 1), new BigDecimal("1000.00"), 10);
    	assertEquals(new Consultant("John Doe", LocalDate.of(2019, 1, 1), new BigDecimal("1000.0"), 10), emp);
    	assertNotEquals(new Consultant("John Doe", LocalDate.of(2019, 1, 1), new BigDecimal("2000.00"), 20), emp);
    }

    // Tests for exceptions (only Consultant fields, Employee fields are in FullTimeEmployeeTest)
    @Test
    void testNullAmount() {
    	Exception e = assertThrows(NullPointerException.class, () -> {
    		new Consultant("Jane Doe", LocalDate.of(2019, 1, 1), null, 10);
    	});
    	assertEquals("Employee contract amount must be positive", e.getMessage());
    }

    @Test
    void testNegativeAmount() {
    	Exception e = assertThrows(IllegalArgumentException.class, () -> {
    		new Consultant("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("-100"), 10);
    	});
    	assertEquals("Employee contract amount must be positive", e.getMessage());
    }

    @Test
    void testZeroAmount() {
    	Exception e = assertThrows(IllegalArgumentException.class, () -> {
    		new Consultant("Jane Doe", LocalDate.of(2019, 1, 1), BigDecimal.ZERO, 10);
    	});
    	assertEquals("Employee contract amount must be positive", e.getMessage());
    }

    @Test
    void testNegativeMonths() {
    	Exception e = assertThrows(IllegalArgumentException.class, () -> {
    		new Consultant("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("100"), -10);
    	});
    	assertEquals("Employee contract length must be positive", e.getMessage());
    }

    @Test
    void testZeroMonths() {
    	Exception e = assertThrows(IllegalArgumentException.class, () -> {
    		new Consultant("Jane Doe", LocalDate.of(2019, 1, 1), new BigDecimal("100"), 0);
    	});
    	assertEquals("Employee contract length must be positive", e.getMessage());
    }

}
