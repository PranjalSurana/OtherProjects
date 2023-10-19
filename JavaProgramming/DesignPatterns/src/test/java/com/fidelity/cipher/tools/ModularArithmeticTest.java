package com.fidelity.cipher.tools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ModularArithmeticTest {

	@ParameterizedTest
	@CsvSource({
		"0123, 0000,     0123", 	// simple addition
		"0123, 0001,     0124",		// simple addition
		"0123, 9987,     9000",		// overflow addition
		"0123, 01010101, 02240224",	// check key repeats
		"9876, 12345,    00004"		// different key
	})
	void testAddition(String key, String addend, String expected) {
		ModularArithmetic ma = new ModularArithmetic(key);
		assertEquals(expected, ma.add(addend));
	}

	@Test
	void testAdditionBase8() {
		ModularArithmetic ma = new ModularArithmetic("0123", 8);
		assertEquals("7000", ma.add("7765"));
	}

	@ParameterizedTest
	@CsvSource({
		"0123, 0246,     0123", 	// simple subtraction
		"0123, 0123,     0000",		// simple subtraction
		"0123, 0000,     0987",		// underflow subtraction
		"0123, 0000888,  0987876",	// check key repeats
		"9876, 12345,    24686"		// different key
	})
	void testSubtraction(String key, String minuend, String expected) {
		ModularArithmetic ma = new ModularArithmetic(key);
		assertEquals(expected, ma.subtract(minuend));
	}

	@Test
	void testSubtractionBase8() {
		ModularArithmetic ma = new ModularArithmetic("0123", 8);
		assertEquals("7765", ma.subtract("7000"));
	}

}
