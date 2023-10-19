package com.fidelity.cipher;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class VigenereAlgorithmImplTest {
	private CipherAlgorithm c;

	@BeforeEach
	void setUp() {
		c = new VigenereAlgorithmImpl();
	}

	@ParameterizedTest
	@CsvSource({
		"'ThE aNsWeR iS 42', 'lL lONOKVCd',          'Hello World'",
		"'ThE aNsWeR iS 42', ' OptagkW78eitujvMP2n', 'What Is The Question'",
		"'RoYgBiV',          'jSJR i1FfJJ',          'Hello World'"
	})
	void testEncrypt(String key, String expected, String plaintext) {
		c.setKey(key);
		assertEquals(expected, c.encrypt(plaintext));
	}

	@ParameterizedTest
	@CsvSource({
		"'ThE aNsWeR iS 42', 'lL lONOKVCd',          'Hello World'",
		"'ThE aNsWeR iS 42', ' OptagkW78eitujvMP2n', 'What Is The Question'",
		"'RoYgBiV',          'jSJR i1FfJJ',          'Hello World'"
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

}
