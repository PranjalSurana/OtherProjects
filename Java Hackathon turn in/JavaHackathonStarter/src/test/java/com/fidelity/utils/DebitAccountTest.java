package com.fidelity.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DebitAccountTest {
	
	private DebitAccount debitAccountTest;

	@Test
	@DisplayName("calculateCurrentBalanceTest: Value is greater than Fee")
	void calculateCurrentBalanceTest_GreaterValue() {
		debitAccountTest = new DebitAccount("acc1", new BigDecimal(12000), new BigDecimal(2600));
		BigDecimal currBalance = debitAccountTest.calculateCurrentBalance();
		assertEquals(new BigDecimal("9400"), currBalance);
	}
	
	@Test
	@DisplayName("calculateCurrentBalanceTest: Value is greater than Fee")
	void calculateCurrentBalanceTest_LessValue() {
		debitAccountTest = new DebitAccount("acc1", new BigDecimal(2000), new BigDecimal(2600));
		BigDecimal currBalance = debitAccountTest.calculateCurrentBalance();
		assertEquals(new BigDecimal("2000"), currBalance);
	}

}