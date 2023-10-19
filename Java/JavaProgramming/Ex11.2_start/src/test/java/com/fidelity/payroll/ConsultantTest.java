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

}
