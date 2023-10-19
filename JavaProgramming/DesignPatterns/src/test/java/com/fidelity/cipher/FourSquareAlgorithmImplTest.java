package com.fidelity.cipher;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FourSquareAlgorithmImplTest {
	private CipherAlgorithm c;

	@BeforeEach
	void setUp() {
		c = new FourSquareAlgorithmImpl();
	}

	@ParameterizedTest
	@CsvSource({
		"'ThE aNsWeR iS 42,RoYgBiV', '5ckkkgPlkrjt',         'Hello World'",
		"'ThE aNsWeR iS 42,RoYgBiV', 'QbLy 8k HhYoDwjlyamn', 'What Is The Question'",
		"'RoYgBiV,ThE aNsWeR iS 42', '9bkkk Tlkqjt',         'Hello World'"
	})
	void testEncrypt(String key, String expected, String plaintext) {
		c.setKey(key);
		assertEquals(expected, c.encrypt(plaintext));
	}

	@ParameterizedTest
	@CsvSource({
		"'ThE aNsWeR iS 42,RoYgBiV', '5ckkkgPlkrjt',         'Hello World'",
		"'ThE aNsWeR iS 42,RoYgBiV', 'QbLy 8k HhYoDwjlyamn', 'What Is The Question'",
		"'RoYgBiV,ThE aNsWeR iS 42', '9bkkk Tlkqjt',         'Hello World'"
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
			c.setKey("ThE;aNsWeR;iS;42,RoYgBiV");
		});
		assertEquals("Key may only contain upper & lower roman alphabet, numbers and space", e.getMessage());
	}

	@Test
	void testMissingKey() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.setKey("ThE aNsWeR iS 42");
		});
		assertEquals("Two keys required, separated by comma", e.getMessage());
	}

	@Test
	void testTooManyKeys() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.setKey("ThE aNsWeR iS 42,RoYgBiV,Aa");
		});
		assertEquals("Two keys required, separated by comma", e.getMessage());
	}

	@Test
	void testInvalidEncryptMessage() {
		c.setKey("ThE aNsWeR iS 42,RoYgBiV");
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.encrypt("Hello.World");
		});
		assertEquals("Message may only contain upper & lower roman alphabet, numbers and space", e.getMessage());
	}

	@Test
	void testInvalidDecryptMessage() {
		c.setKey("ThE aNsWeR iS 42,RoYgBiV");
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.decrypt("H");
		});
		assertEquals("Four-square messages to be decrypted must have even length", e.getMessage());
	}

}
