package com.fidelity.cipher;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SimpleCaesarAlgorithmImplTest {
	private CipherAlgorithm c;

	@BeforeEach
	void setUp() {
		c = new SimpleCaesarAlgorithmImpl();
	}

	@ParameterizedTest
	@CsvSource({
		"'1', 'Ifmmp0Xpsme',          'Hello World'",
		"'1', 'Xibu0Jt0Uif0Rvftujpo', 'What Is The Question'",
		"'2', 'Jgnnq1Yqtnf',          'Hello World'"
	})
	void testEncrypt(String key, String expected, String plaintext) {
		c.setKey(key);
		assertEquals(expected, c.encrypt(plaintext));
	}

	@ParameterizedTest
	@CsvSource({
		"'1', 'Ifmmp0Xpsme',          'Hello World'",
		"'1', 'Xibu0Jt0Uif0Rvftujpo', 'What Is The Question'",
		"'2', 'Jgnnq1Yqtnf',          'Hello World'"
	})
	void testDecrypt(String key, String ciphertext, String expected) {
		c.setKey(key);
		assertEquals(expected, c.decrypt(ciphertext));
	}

	@Test
	void testKeyNotSetEncrypt() {
		Exception e = assertThrows(IllegalStateException.class, () -> {
			c.encrypt("Hello World");
		});
		assertEquals("Key not set", e.getMessage());
	}

	@Test
	void testKeyNotSetDecrypt() {
		Exception e = assertThrows(IllegalStateException.class, () -> {
			c.decrypt("Hello World");
		});
		assertEquals("Key not set", e.getMessage());
	}

	@Test
	void testInvalidKey() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.setKey("ThE aNsWeR iS 42");
		});
		assertEquals("Key is numeric offset in range 0 to 62", e.getMessage());
	}

	@Test
	void testKeyOutOfRangeLow() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.setKey("-1");
		});
		assertEquals("Key is numeric offset in range 0 to 62", e.getMessage());
	}

	@Test
	void testKeyOutOfRangeHigh() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.setKey("63");
		});
		assertEquals("Key is numeric offset in range 0 to 62", e.getMessage());
	}

}
