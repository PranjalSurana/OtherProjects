package com.fidelity.ship;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OfficerTest {

	Officer officer;
	
	@Test
	@DisplayName("calcPayTest: Success")
	void calcPayTest_Success() {
		officer = new Officer(2, "officer", 10, "DGP", new BigDecimal("10000"));
		assertEquals(new BigDecimal("310000"), officer.calcPay(LocalDate.of(2023, 12, 11)));
	}
	
	@Test
	@DisplayName("calcPayTest: Curr Day less than payday")
	void calcPayTest_currDayLess() {
		officer = new Officer(2, "officer", 10, "DGP", new BigDecimal("10000"));
		assertEquals(new BigDecimal("10000"), officer.calcPay(LocalDate.of(2023, 2, 9)));
	}
	
}