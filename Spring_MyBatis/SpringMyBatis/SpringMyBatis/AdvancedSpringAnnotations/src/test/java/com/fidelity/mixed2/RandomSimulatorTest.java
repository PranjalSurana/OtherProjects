package com.fidelity.mixed2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;


/*
 * Mixed XML and AppConfig with AppConfig taking the lead
 */
class RandomSimulatorTest {
	private AbstractApplicationContext factory;
	private RandomSimulator r1;
	private RandomSimulator r2;

	@BeforeEach
	void setUp() {
		factory = new AnnotationConfigApplicationContext(AppConfig.class);
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
	
	@Test
	void testRand3() {
		System.out.println(factory.getBean("rand3"));
	}
}
