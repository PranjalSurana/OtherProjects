package com.fidelity.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculatorTest {
	private Calculator calc;
	
	@BeforeEach
	void setUp() {
		calc = new Calculator();
	}

	@Test
	void testNormalOperation() throws CalculationException {
		assertEquals(2, calc.parseAndCalculateResult("4", "2"));
	}

	@Test
	void testNullString() {
		Exception e = assertThrows(CalculationException.class, () -> {
			calc.parseAndCalculateResult(null, "2");
		});
		assertTrue(e.getCause() instanceof NullPointerException);
	}

	@Test
	void testNotANumber() {
		Exception e = assertThrows(CalculationException.class, () -> {
			calc.parseAndCalculateResult("a", "2");
		});
		assertTrue(e.getCause() instanceof NumberFormatException);
	}
	
	@Test
	void testDivideByZero() {
		Exception e = assertThrows(CalculationException.class, () -> {
			calc.parseAndCalculateResult("4", "0");
		});
		assertTrue(e.getCause() instanceof ArithmeticException);
	}}
