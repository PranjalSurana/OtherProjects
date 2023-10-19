package com.fidelity.incomecalculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IncomeCalculatorTest {
	
	private IncomeCalculator incomeCalculator;
	
	@BeforeEach
	void setUp() {
		incomeCalculator = new IncomeCalculator();
	}

	@Test
	void testLongTermFourHoursNoVacation() {
		assertEquals(2600.0, incomeCalculator.getAnnualPay(4, 2.5, 0), 0.01);
	}
	
	@Test
	void testLongTermThreeHoursNoVacation() {
		assertEquals(1950.0, incomeCalculator.getAnnualPay(3, 2.5, 0), 0.01);
	}
	
	@Test
	void testLongTermThreeHoursTwoWeeks() {
		assertEquals( 1875.0, incomeCalculator.getAnnualPay(3, 2.5, 10), 0.01);
	}
	
	@Test
	void testLongTermThreeHoursTwoWeeksAtThree() {
		assertEquals(2250.0, incomeCalculator.getAnnualPay(3, 3.0, 10), 0.01);
	}

	@Test
	void testShortTermFourHours100Days() {
		assertEquals(1000.0, incomeCalculator.getAnnualPay(100, 4, 2.5), 0.01);
	}
	
	@Test
	void testShortTermFourHours200Days() {
		assertEquals(2000.0, incomeCalculator.getAnnualPay(200, 4, 2.5), 0.01);
	}
	
	@Test
	void testShortTermFiveHours200Days() {
		assertEquals(2500.0, incomeCalculator.getAnnualPay(200, 5, 2.5), 0.01);
	}
	
	@Test
	void testShortTermFiveHours200DaysAtThree() {
		assertEquals(3000.0, incomeCalculator.getAnnualPay(200, 5, 3.0), 0.01);
	}

	@Test
	void testNegativeHoursPerDay() {
		assertEquals(0.0, incomeCalculator.getAnnualPay(200, -1, 3.0), 0.01);
	}
	
	@Test
	void testNegativeHourlyPay() {
		assertEquals(0.0, incomeCalculator.getAnnualPay(200, 5, -1.0), 0.01);
	}
	
	@Test
	void testNegativeDaysWorked() {
		assertEquals(0.0, incomeCalculator.getAnnualPay(-1, 5, 3.0), 0.01);
	}

	@Test
	void testNegativeUnpaidHolidays() {
		assertEquals(0.0, incomeCalculator.getAnnualPay(3, 3.0, -1), 0.01);
	}

	@Test
	void testNegativeHoursPerDayLongTerm() {
		assertEquals(0.0, incomeCalculator.getAnnualPay(-1, 3.0, 0), 0.01);
	}

	@Test
	void testNegativeHourlyPayLongTerm() {
		assertEquals(0.0, incomeCalculator.getAnnualPay(3, -1.0, 0), 0.01);
	}

	@Test
	void testZeroHoursPerDayShortTerm() {
		assertEquals(0.0, incomeCalculator.getAnnualPay(200, 0, 3.0), 0.01);
	}

	@Test
	void testZeroHourlyPayShortTerm() {
		assertEquals(0.0, incomeCalculator.getAnnualPay(200, 5, 0.0), 0.01);
	}

	@Test
	void testZeroHoursPerDayLongTerm() {
		assertEquals(0.0, incomeCalculator.getAnnualPay(0, 3.0, 0), 0.01);
	}

	@Test
	void testZeroHourlyPayLongTerm() {
		assertEquals(0.0, incomeCalculator.getAnnualPay(3, 0.0, 0), 0.01);
	}

}