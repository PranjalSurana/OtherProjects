package com.fidelity.classes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ClassUsageTest {

	@Test
	void testUseClasses() {
		Object myObject = ClassUsage.useClasses();
		assertTrue(myObject instanceof String);
		assertEquals(myObject, "Hello World");
	}

}
