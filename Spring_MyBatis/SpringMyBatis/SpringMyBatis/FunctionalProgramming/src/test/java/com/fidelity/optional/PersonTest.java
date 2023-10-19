package com.fidelity.optional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PersonTest {
	@Test
	//  the test to verify getPhoneNumber works correctly
	//  when the phone number is not null
	public void testGetPhoneNumber() {
		String expected = "123-4567";
		Person person = new Person("Grady", "Booch", expected);
		String actual = person.getPhoneNumber().orElse("no phone");
		assertEquals(expected, actual);
	}
	
	@Test
	// TODO: write the test to verify getPhoneNumber works correctly
	//       when the phone number is null
	public void testGetNullPhoneNumber() {
		fail("testGetNullPhoneNumber is not defined");
	}
	
	@Test
	// TODO: write the test to find the person with a given phone number
	public void testFindPerson() {
		fail("testFindPerson is not defined");
	}

}
