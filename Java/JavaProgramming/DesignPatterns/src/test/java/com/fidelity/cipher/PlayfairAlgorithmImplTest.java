package com.fidelity.cipher;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PlayfairAlgorithmImplTest {
	private CipherAlgorithm c;

	@BeforeEach
	void setUp() {
		c = new PlayfairAlgorithmImpl();
	}

	@ParameterizedTest
	@CsvSource({
		"'ThE aNsWeR iS 42', 'C4rumpaTpkoZ',         'Hello World'",
		"'ThE aNsWeR iS 42', 'TETxNGWahESTMx0Tvepo', 'What Is The Question'",
		"'RoYgBiV',          'Lasuu1iZVlnb',         'Hello World'",
		"'RoYgBiV',          'XcVlut',               'Short'"

	})
	void testEncrypt(String key, String expected, String plaintext) {
		c.setKey(key);
		assertEquals(expected, c.encrypt(plaintext));
	}

	@ParameterizedTest
	@CsvSource({
		"'ThE aNsWeR iS 42', 'C4rumpaTpkoZ',         'Hello World'",
		"'ThE aNsWeR iS 42', 'TETxNGWahESTMx0Tvepo', 'What Is The Question'",
		"'RoYgBiV',          'Lasuu1iZVlnb',         'Hello World'",
		"'RoYgBiV',          'XcVlut',               'Short'"

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
			c.setKey("ThE;aNsWeR;iS;42");
		});
		assertEquals("Key may only contain upper & lower roman alphabet, numbers and space", e.getMessage());
	}

	@Test
	void testInvalidEncryptMessage() {
		c.setKey("ThE aNsWeR iS 42");
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.encrypt("Hello.World");
		});
		assertEquals("Message may only contain upper & lower roman alphabet, numbers and space", e.getMessage());
	}

	@Test
	void testInvalidDecryptMessage() {
		c.setKey("ThE aNsWeR iS 42");
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.decrypt("H");
		});
		assertEquals("Playfair messages to be decrypted must have even length", e.getMessage());
	}

}
