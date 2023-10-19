package com.fidelity.email;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmailGeneratorTest {

	@Test
	public void testSimple() {
		EmailGenerator g = new EmailGenerator();
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
}
