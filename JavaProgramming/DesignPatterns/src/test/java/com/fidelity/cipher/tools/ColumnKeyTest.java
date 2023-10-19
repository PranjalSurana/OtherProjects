package com.fidelity.cipher.tools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ColumnKeyTest {

	@Test
	void testKeyOrderFromText1() {
		int[] expected = {4, 0, 6, 2, 3, 5, 1};
		assertArrayEquals(expected, ColumnKey.keyOrderFromText("RoYgBiV", false));
	}

	@Test
	void testKeyOrderFromText2() {
		int[] expected = {3, 13, 12, 2, 5, 9, 11, 0, 7, 4, 8, 1, 10, 6};
		assertArrayEquals(expected, ColumnKey.keyOrderFromText("ThE aNsWeR iS 42", false));
	}

	@Test
	void testKeyOrderFromTextWithDuplicates() {
		int[] expected = {3, 10, 13, 15, 14, 2, 5, 9, 12, 0, 7, 4, 8, 1, 11, 6};
		assertArrayEquals(expected, ColumnKey.keyOrderFromText("ThE aNsWeR iS 42", true));
	}
	
	@ParameterizedTest
	@CsvSource({
		"'RoYgBiV',          '1634052'",
		"'ThE aNsWeR iS 42', '711309413810512621'"
	})
	void testKeyFromTextNoDuplicates(String key, String expected) {
		assertEquals(expected, ColumnKey.keyFromText(key, false));
	}

	@ParameterizedTest
	@CsvSource({
		"'RoYgBiV',          '1634052'",
		"'ThE aNsWeR iS 42', '9135011615101271148243'"
	})
	void testKeyFromTextDuplicates(String key, String expected) {
		assertEquals(expected, ColumnKey.keyFromText(key, true));
	}

	@ParameterizedTest
	@CsvSource({
		"'RoYgBiV',          '1634052'",
		"'ThE aNsWeR iS 42', '71309438052621'"
	})
	void testKeyFromTextModulo10NoDuplicates(String key, String expected) {
		assertEquals(expected, ColumnKey.keyFromTextModulo10(key, false));
	}

	@ParameterizedTest
	@CsvSource({
		"'RoYgBiV',          '1634052'",
		"'ThE aNsWeR iS 42', '9350165027148243'"
	})
	void testKeyFromTextModulo10Duplicates(String key, String expected) {
		assertEquals(expected, ColumnKey.keyFromTextModulo10(key, true));
	}

}
