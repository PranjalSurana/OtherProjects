package com.fidelity.cipher.tools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PolybiusSquareTest {

	private PolybiusSquare ps;

	@BeforeEach
	void setUp() {
		ps = new PolybiusSquare("ThE aNsWeR iS 42");
	}

	@ParameterizedTest
	@CsvSource({
		"A, 2, 6",
		"Z, 5, 1",
		"z, 7, 6"
	})
	void testFindChar(char ch, int row, int col) {
		PolybiusSquare.RowCol rc = ps.findChar(ch);
		assertEquals(row, rc.getRow());
		assertEquals(col, rc.getCol());
	}

	@Test
	void testFindCharDifferentKey() {
		ps = new PolybiusSquare("RoYgBiV");
		PolybiusSquare.RowCol rc = ps.findChar('Q');
		assertEquals(4, rc.getRow());
		assertEquals(1, rc.getCol());
	}
	
	@ParameterizedTest
	@CsvSource({
		"A, 2, 6",
		"Z, 5, 1",
		"z, 7, 6"
	})
	void testCharAtRc(char ch, int row, int col) {
		PolybiusSquare.RowCol rc = new PolybiusSquare.RowCol(row, col);
		assertEquals(ch, ps.charAt(rc));
	}

	@Test
	void testCharAtRcDifferentKey() {
		ps = new PolybiusSquare("RoYgBiV");
		assertEquals('Q', ps.charAt(new PolybiusSquare.RowCol(4, 1)));
	}

	@ParameterizedTest
	@CsvSource({
		"A, 2, 6",
		"Z, 5, 1",
		"z, 7, 6"
	})
	void testCharAt(char ch, int row, int col) {
		assertEquals(ch, ps.charAt(row, col));
	}

	@Test
	void testCharAtDifferentKey() {
		ps = new PolybiusSquare("RoYgBiV");
		assertEquals('Q', ps.charAt(4, 1));
	}

}
