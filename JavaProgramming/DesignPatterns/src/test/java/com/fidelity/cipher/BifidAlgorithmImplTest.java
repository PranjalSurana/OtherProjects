package com.fidelity.cipher;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BifidAlgorithmImplTest {
	private CipherAlgorithm c;

	@BeforeEach
	void setUp() {
		c = new BifidAlgorithmImpl();
	}

	@ParameterizedTest
	@CsvSource({
		"'ThE aNsWeR iS 42,7', 'Dqkah4KqpX4',          'Hello World'",
		"'ThE aNsWeR iS 42,7', 'TW W4 gThawh MeuqsEP', 'What Is The Question'",
		"'RoYgBiV,7',          'MrRPQ1yVq63',          'Hello World'"
	})
	void testEncrypt(String key, String expected, String plaintext) {
		c.setKey(key);
		assertEquals(expected, c.encrypt(plaintext));
	}

	@ParameterizedTest
	@CsvSource({
		"'ThE aNsWeR iS 42,7', 'Dqkah4KqpX4',          'Hello World'",
		"'ThE aNsWeR iS 42,7', 'TW W4 gThawh MeuqsEP', 'What Is The Question'",
		"'RoYgBiV,7',          'MrRPQ1yVq63',          'Hello World'"
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
		assertEquals("Keys not set", e.getMessage());
	}

	@Test
	void testKeyNotSetDecrypt() {
		Exception e = assertThrows(IllegalStateException.class, () -> {
			c.decrypt("Hello World");
		});
		assertEquals("Keys not set", e.getMessage());
	}

	@Test
	void testInvalidKey() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.setKey("ThE;aNsWeR;iS;42,7");
		});
		assertEquals("Key may only contain upper & lower roman alphabet, numbers and space", e.getMessage());
	}

	@Test
	void testMissingKey() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.setKey("ThE aNsWeR iS 42");
		});
		assertEquals("Require key and period, separated by comma", e.getMessage());
	}

	@Test
	void testTooManyKeys() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.setKey("ThE aNsWeR iS 42,RoYgBiV,Aa");
		});
		assertEquals("Require key and period, separated by comma", e.getMessage());
	}

	@Test
	void testNonNumericPeriod() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.setKey("ThE aNsWeR iS 42,RoYgBiV");
		});
		assertEquals("Period must be numeric", e.getMessage());
	}

	@Test
	void testInvalidEncryptMessage1() {
		c.setKey("ThE aNsWeR iS 42,7");
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.encrypt("Hello.World");
		});
		assertEquals("Message may only contain upper & lower roman alphabet, numbers and space", e.getMessage());
	}

	@Test
	void testInvalidEncryptMessage2() {
		c.setKey("ThE aNsWeR iS 42,7");
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.encrypt("Hello/World");
		});
		assertEquals("Message may only contain upper & lower roman alphabet, numbers and space", e.getMessage());
	}

}
