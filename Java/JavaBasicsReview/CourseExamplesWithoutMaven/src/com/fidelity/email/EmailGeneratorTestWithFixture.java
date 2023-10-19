package com.fidelity.email;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class EmailGeneratorTestWithFixture {

	private EmailGenerator g;
	
	@BeforeEach
	public void setUp() {
		g = new EmailGenerator();
	}

	@AfterEach
	public void tearDown() {
		g = null; // Not needed in this case
	}
	
	@Test
	public void testSimple() {
		assertEquals("doe.jane@fidelity.com", g.makeEmailFromName("Jane Doe"));
	}

	@Test
	public void testExtraMiddleSpaces() {
		EmailGenerator g = new EmailGenerator();
		assertEquals("doe.jane@fidelity.com", g.makeEmailFromName("Jane   Doe"));
	}

	@Test
	public void testExtraStartingSpaces() {
		EmailGenerator g = new EmailGenerator();
		assertEquals("doe.jane@fidelity.com", g.makeEmailFromName("  Jane Doe"));
	}

	@Test
	public void testExtraEndingSpaces() {
		EmailGenerator g = new EmailGenerator();
		assertEquals("doe.jane@fidelity.com", g.makeEmailFromName("Jane Doe   "));
	}

	@Test
	public void testPunctuation() {
		EmailGenerator g = new EmailGenerator();
		assertEquals("o'malley.frank@fidelity.com", g.makeEmailFromName("Frank O'Malley"));
	}
	
	@Test
	@Disabled("Not yet implemented")
	public void testAnotherThing() {
		fail("Not yet implemented");
	}

}
