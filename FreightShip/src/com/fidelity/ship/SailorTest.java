package com.fidelity.ship;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SailorTest {

	Sailor sailor;
	
	@Test
	@DisplayName("calcPayTest: Success")
	void calcPayTest_Success() {
		sailor = new Sailor(1, "sailor", 10, "Engineer", new BigDecimal("100"), 100);
		assertEquals(new BigDecimal("310000"), sailor.calcPay(LocalDate.of(2023, 12, 11)));
	}
	
	@Test
	@DisplayName("calcPayTest: Curr Day less than payday")
	void calcPayTest_currDayLess() {
		sailor = new Sailor(1, "sailor", 10, "Engineer", new BigDecimal("100"), 100);
		assertEquals(new BigDecimal("10000"), sailor.calcPay(LocalDate.of(2023, 2, 9)));
	}

}