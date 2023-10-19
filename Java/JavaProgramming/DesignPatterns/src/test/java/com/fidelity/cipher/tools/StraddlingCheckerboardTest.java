package com.fidelity.cipher.tools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StraddlingCheckerboardTest {

	private StraddlingCheckerboard sc;

	@BeforeEach
	void setUp() {
		sc = new StraddlingCheckerboard("3517269");
	}

	@ParameterizedTest
	@CsvSource({
		"a, 2",
		"A, 39",
		"L, 59",
		"' ', 312",
		"m, 373",
		"q, 523",
		"X, 565"
	})
	void testFindChar(char ch, String expected) {
		assertEquals(expected, sc.findChar(ch));
	}
	
	@Test
	void testFindInvalidChar() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			sc.findChar('.');
		});
		assertEquals("Character may only be upper & lower roman alphabet, numbers and space", e.getMessage());
	}

	@ParameterizedTest
	@CsvSource({
		"a, 2",
		"A, 39",
		"L, 59",
		"' ', 312",
		"m, 373",
		"q, 523",
		"X, 565"
	})
	void testCharAt(char expected, String value) {
		assertEquals(expected, sc.charAt(value));
	}

	@Test
	void testInvalidCharAt() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			sc.charAt("37");
		});
		assertEquals("Unrecognized character code", e.getMessage());
	}

	@Test
	void testEncryptHelloWorld() {
		assertEquals("57033334312529493332", sc.encrypt("Hello World"));
	}
	
	@Test
	void testEncryptNowIsTheTime() {
		assertEquals("5343763126831213003121637303123744931223333312377443231237307312143123"
				+ "543730312143121300312263231243743121300312378291375", 
				sc.encrypt("Now is the time for all good men to come to the aid of the party"));
	}
	
	@Test
	void testDecryptHelloWorld() {
		assertEquals("Hello World", sc.decrypt("57033334312529493332"));
	}

	@Test
	void testDecryptNowIsTheTime() {
		assertEquals("Now is the time for all good men to come to the aid of the party",
				sc.decrypt("5343763126831213003121637303123744931223333312377443231237307312143123"
						+ "543730312143121300312263231243743121300312378291375"));
	}
	
	@Test
	void testInvalidKey() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			sc = new StraddlingCheckerboard("");
		});
		assertEquals("Key must be 7 digits long", e.getMessage());
	}

	@Test
	void testInvalidAlphaKey() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			sc = new StraddlingCheckerboard("ABCDEFG");
		});
		assertEquals("Key must be 7 digits long", e.getMessage());
	}

	@Test
	void testShortKey() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			sc = new StraddlingCheckerboard("123456");
		});
		assertEquals("Key must be 7 digits long", e.getMessage());
	}

	@Test
	void testNullKey() {
		Exception e = assertThrows(NullPointerException.class, () -> {
			sc = new StraddlingCheckerboard(null);
		});
		assertEquals("Key may not be null", e.getMessage());
	}

	@Test
	void testInvalidEncryptMessage() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			sc.encrypt("Hello.World");
		});
		assertEquals("Message may only contain upper & lower roman alphabet, numbers and space", e.getMessage());
	}

	@Test
	void testInvalidDecryptMessage() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			sc.decrypt("H");
		});
		assertEquals("Ciphertext may only contain digits", e.getMessage());
	}
	
}
