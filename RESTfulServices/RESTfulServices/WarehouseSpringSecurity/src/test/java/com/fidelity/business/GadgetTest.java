package com.fidelity.business;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class GadgetTest {

	@Test
	void testPriceScale() {
		Gadget gadget = new Gadget(1, "Test Gadget", new BigDecimal("42.0"), 4);
		assertEquals(new Gadget(1, "Test Gadget", new BigDecimal("42.00"), 4), gadget);
	}

}
