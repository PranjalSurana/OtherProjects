package com.fidelity.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MentorTest {
	@Test
	void testFullName() {
		String expected = "Jane Doe";
		Mentor mentor = new Mentor("Jane", "Doe", 1);
		String actual = mentor.getFullName();
		assertEquals(expected, actual, "Full name should be Jane Doe");
	}
	
	@Test
	void testInvalidId() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Mentor("Aadya", "Kumar", -1);
		});
	}
	
	@Test
	void testInvalidIdMessage() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			new Mentor("Aadya", "Kumar", -1);
		});
		assertEquals("id may not be negative", e.getMessage(), 
				"Expected appropriate message");
	}

}
