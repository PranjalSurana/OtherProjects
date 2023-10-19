package com.fidelity.incomecalculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class IncomeCalculatorXParameterizedTest {
	private IncomeCalculator ic;
	
	@BeforeEach
	void setUp() {
		ic = new IncomeCalculator();
	}

	/*
	 * Parameterized tests are an experimental feature of JUnit 5.
	 * 
	 * Each parameterized test has a corresponding source. The simplest sources are @ValueSource,
	 * which provides a list of values of the same type, and @CsvSource, which provides a list of
	 * strings, each of which contains a comma-separated list of values that may be implicitly 
	 * converted to different data types.
	 * 
	 * https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests
	 */
	@ParameterizedTest
	@CsvSource({
		"2600.0, 4, 2.5, 0",
		"1950.0, 3, 2.5, 0",
		"1875.0, 3, 2.5, 10",
		"2250.0, 3, 3.0, 10",
		// Negative test: negative unpaid holidays
		"0.0, 3, 3.0, -1",
		// Guard test: negative hours per day (already tested by short term)
		"0.0, -1, 3.0, 0",
		// Guard test: negative hourly pay (already tested by short term)
		"0.0, 3, -1.0, 0",
		// Guard test: zero hours per day (already tested by short term)
		"0.0, 0, 3.0, 0",
		// Guard test: zero hourly pay (already tested by short term)
		"0.0, 3, 0.0, 0"
	})
	void testLongTerm(double expected, int hoursPerDay, double payPerHour, int daysUnpaidLeave) {
		assertEquals(expected, ic.getAnnualPay(hoursPerDay, payPerHour, daysUnpaidLeave), 0.01);
	}

	@ParameterizedTest
	@CsvSource({
		"1000.0, 100, 4, 2.5",
		"2000.0, 200, 4, 2.5",
		"2500.0, 200, 5, 2.5",
		"3000.0, 200, 5, 3.0",
		// Negative test: negative hours per day
		"0.0, 200, -1, 3.0",
		// Negative test: negative hourly pay
		"0.0, 200, 5, -1.0",
		// Negative test: negative days worked
		"0.0, -1, 5, 3.0",
		// Guard test: zero hours per day (already included in calculation)
		"0.0, 200, 0, 3.0",
		// Guard test: zero hourly pay (already included in calculation)
		"0.0, 200, 5, 0.0"
	})
	void testShortTerm(double expected, int daysWorked, int hoursPerDay, double payPerHour) {
		assertEquals(expected, ic.getAnnualPay(daysWorked, hoursPerDay, payPerHour), 0.01);
	}

	/*
	 * Negative tests are now interspersed with the positive tests, but commented above
	 * 
	 * We don't allow:
	 * a. Zero or negative value for hours worked per day and hourly pay
	 * b. Negative value for number of unpaid holidays and number of days worked
	 * 
	 * Since we don't have a better way of indicating the problem, we will set pay to 0.0.
	 * Zero values will take care of themselves, so we will concentrate on negatives.
	 */


	/*
	 * The guard tests are now interspersed with positive tests, as with negative.
	 * 
	 * Here are some scenarios we didn't need to write since they are already
	 * covered. Some are covered because they involve a zero multiplier and
	 * others because the long term calculation calls the short term one.
	 * 
	 * We can add them as a guard against future changes to our class.
	 */
}
