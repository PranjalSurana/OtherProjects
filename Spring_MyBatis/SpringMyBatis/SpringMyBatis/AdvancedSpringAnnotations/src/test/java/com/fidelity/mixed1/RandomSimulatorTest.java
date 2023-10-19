package com.fidelity.mixed1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * Mixed XML and AppConfig with XML taking the lead
 */

class RandomSimulatorTest {
	private AbstractApplicationContext factory;
	private RandomSimulator r1;
	private RandomSimulator r2;

	@BeforeEach
	void setUp() {
		String springConfigurationFile = "rand-beans.xml";
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

	@Test
	void testSharedSeedByValueAnnotation() {
		RandomSimulator r1v = factory.getBean("rand1v", RandomSimulator.class);
		RandomSimulator r2v = factory.getBean("rand2v", RandomSimulator.class);
		assertEquals(r1v.getSeed(), r2v.getSeed(), 0.0000000001);
	}

	/*
	 * This is extremely difficult to do, which is why it is separated into AppConfigByExpressionEvaluation
	 */
	@Test
	void testSharedSeedByExpressionEvaluation() {
		RandomSimulator r1x = factory.getBean("rand1x", RandomSimulator.class);
		RandomSimulator r2x = factory.getBean("rand2x", RandomSimulator.class);
		assertEquals(r1x.getSeed(), r2x.getSeed(), 0.0000000001);
	}

}
