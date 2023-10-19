package com.fidelity.cipher;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CaesarKeyAlgorithmImplTest {
	private CipherAlgorithm c;
	
	@BeforeEach
	void setUp() {
		c = new CaesarKeyAlgorithmImpl();
	}

	@ParameterizedTest
	@CsvSource({
		"'ThE aNsWeR iS 42', 'vPHHDTcDAHQ',          'Hello World'",
		"'ThE aNsWeR iS 42', 'cLX8Tu9TgLPTl7P98KDF', 'What Is The Question'",
		"'RoYgBiV',          'nLEEARUA7EM',          'Hello World'"
	})
	void testEncrypt(String key, String expected, String plaintext) {
		c.setKey(key);
		assertEquals(expected, c.encrypt(plaintext));
	}

	@ParameterizedTest
	@CsvSource({
		"'ThE aNsWeR iS 42', 'vPHHDTcDAHQ',          'Hello World'",
		"'ThE aNsWeR iS 42', 'cLX8Tu9TgLPTl7P98KDF', 'What Is The Question'",
		"'RoYgBiV',          'nLEEARUA7EM',          'Hello World'"
	})
	void testDecrypt(String key, String ciphertext, String expected) {
		c.setKey(key);
		assertEquals(expected, c.decrypt(ciphertext));
	}

	@Test
	void testInvalidKey() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.setKey("ThE;aNsWeR;iS;42");
		});
		assertEquals("Key may only contain upper & lower roman alphabet, numbers and space", e.getMessage());
	}

}
