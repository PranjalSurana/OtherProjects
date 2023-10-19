package com.fidelity.simpletest;

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

	@Test
    void dependentAssertions() {
		Mentor mentor = new Mentor("Jane", "Doe");
        // Within a code block, if an assertion fails the
        // subsequent code in the same block will be skipped.
        assertAll("Check properties",
            () -> {
                String firstName = mentor.getFirstName();
                assertNotNull(firstName);

                // Executed only if the previous assertion is valid.
                assertAll("first name",
                    () -> assertTrue(firstName.startsWith("J")),
                    () -> assertTrue(firstName.endsWith("e"))
                );
            },
            () -> {
                // Grouped assertion, so processed independently
                // of results of first name assertions.
                String lastName = mentor.getLastName();
                assertNotNull(lastName);

                // Executed only if the previous assertion is valid.
                assertAll("last name",
                    () -> assertTrue(lastName.startsWith("D")),
                    () -> assertTrue(lastName.endsWith("e"))
                );
            }
        );
    }

}
