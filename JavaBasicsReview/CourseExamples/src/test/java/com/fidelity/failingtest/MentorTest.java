package com.fidelity.failingtest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MentorTest {

	@Test
	void testFullName() {
		String expected = "Jane Doe";
		Mentor mentor = new Mentor("Jane", "Doe");
		String actual = mentor.getFullName();
		assertEquals(expected, actual, "Full name should be Jane Doe");
	}

}
