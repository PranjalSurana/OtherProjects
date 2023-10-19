package com.fidelity.advanced;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class RandomSimulatorTest {
	private AbstractApplicationContext factory;
	private RandomSimulator r1;
	private RandomSimulator r2;

	@BeforeEach
	void setUp() {
		String springConfigurationFile = "spel-beans.xml";
		factory = new ClassPathXmlApplicationContext(springConfigurationFile);
		r1 = factory.getBean("rand1", RandomSimulator.class);
		r2 = factory.getBean("rand2", RandomSimulator.class);
	}
	
	@AfterEach
	void tearDown() {
		factory.close();
	}

	@Test
	void testRandomSimulator() {
		double actual = r1.getSeed();
		System.out.println(actual);
		assertTrue(actual >= 0.0 && actual <= 100.00);
	}

	@Test
	void testSharedSeed() {
		assertEquals(r1.getSeed(), r2.getSeed(), 0.0000000001);
	}

}
