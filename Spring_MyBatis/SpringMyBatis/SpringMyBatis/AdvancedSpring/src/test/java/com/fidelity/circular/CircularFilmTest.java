package com.fidelity.circular;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * This class follows the same form as the others, but because there is a circular reference, we cannot
 * even load the context. For that reason we have moved the context creation into a test where we can
 * check that the right exception is thrown.
 */
class CircularFilmTest {
	private AbstractApplicationContext factory;
	private Film film;

	@BeforeEach
	void setUp() {
		// normally we would initialise here, but that doesn't work: see the test below
//		String springConfigurationFile = "circular-beans.xml";
//		factory = new ClassPathXmlApplicationContext(springConfigurationFile);
//		film = factory.getBean("no-way", Film.class);
	}
	
	@AfterEach
	void tearDown() {
//		factory.close();
	}
	
	@Test
	void testCannotResolveCircularReference() {
		assertThrows(BeanCreationException.class, () -> {
			String springConfigurationFile = "circular-beans.xml";
			factory = new ClassPathXmlApplicationContext(springConfigurationFile);
			film = factory.getBean("no-way", Film.class);
		});
	}
	
	@Disabled
	@Test
	void testTitle() {
		assertEquals("Lawrence Gump and the Pirate Wall of Quantico", film.getTitle());
	}

	@Disabled
	@Test
	void testCast() {
		List<Role> actual = film.getCast();
		assertEquals(3, actual.size());
		assertTrue(actual.contains(new Role(film, "Alex Parrish", new Actor("Priyanka", "Chopra"))));
	}

}
