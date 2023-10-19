package com.fidelity.cipher;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SovietNihilistAlgorithmImplTest {
	private CipherAlgorithm c;

	@BeforeEach
	void setUp() {
		c = new SovietNihilistAlgorithmImpl();
	}

	@Test
	void testEncryptHelloWorld() {
		c.setKey("3517269,Nihilist");
		assertEquals("78789 86805 89631 18797", c.encrypt("Hello World"));
	}

	@Test
	void testDecryptHelloWorld() {
		c.setKey("3517269,Nihilist");
		assertEquals("Hello World", c.decrypt("78789 86805 89631 18797"));
	}

	@Test
	void testEncryptNowIsTheTime() {
		c.setKey("3517269,Nihilist");
		assertEquals("08388 33585 08387 78984 79830 87073 77688 04784 69992 68313 87688 "
				+ "90772 87967 69823 94765 89654 78175 98604 94886 27735 25765 17792 82910 63708 1", 
				c.encrypt("Now is the time for all good men to come to the aid of the party"));
		
	}

	@Test
	void testDecryptNowIsTheTime() {
		c.setKey("3517269,Nihilist");
		assertEquals("Now is the time for all good men to come to the aid of the party",
				c.decrypt("08388 33585 08387 78984 79830 87073 77688 04784 69992 68313 87688 "
				+ "90772 87967 69823 94765 89654 78175 98604 94886 27735 25765 17792 82910 63708 1"));
		
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
			c.setKey("3517269,N;hilist");
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
			c.setKey("3517269,Nihilist,Aa");
		});
		assertEquals("Two keys required, separated by comma", e.getMessage());
	}

	@Test
	void testInvalidEncryptMessage() {
		c.setKey("3517269,Nihilist");
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.encrypt("Hello.World");
		});
		assertEquals("Message may only contain upper & lower roman alphabet, numbers and space", e.getMessage());
	}

	@Test
	void testInvalidDecryptMessage() {
		c.setKey("3517269,Nihilist");
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			c.decrypt("H");
		});
		assertEquals("Nihilist messages to be decrypted must contain only digits and space", e.getMessage());
	}


}
