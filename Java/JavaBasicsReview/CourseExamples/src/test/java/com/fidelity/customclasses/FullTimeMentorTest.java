package com.fidelity.customclasses;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FullTimeMentorTest {

	@Test
	void testFullName() {
		String expected = "Jane Doe";
		Mentor mentor = new FullTimeMentor("Jane", "Doe", 42.42);
		String actual = mentor.getFullName();
		assertEquals(expected, actual, "Full name should be Jane Doe");
	}

}
