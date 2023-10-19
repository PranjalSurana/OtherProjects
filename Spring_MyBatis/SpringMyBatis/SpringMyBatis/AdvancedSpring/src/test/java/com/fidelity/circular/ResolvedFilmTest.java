package com.fidelity.circular;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


class ResolvedFilmTest {
	private AbstractApplicationContext factory;
	private Film film;

	@BeforeEach
	void setUp() {
		String springConfigurationFile = "circular-resolved-beans.xml";
		factory = new ClassPathXmlApplicationContext(springConfigurationFile);
		film = factory.getBean("no-way", Film.class);
	}
	
	@AfterEach
	void tearDown() {
		factory.close();
	}
	

	@Test
	void testTitle() {
		assertEquals("Lawrence Gump and the Pirate Wall of Quantico", film.getTitle());
	}

	@Test
	void testCast() {
		List<Role> actual = film.getCast();
		assertEquals(3, actual.size());
		assertTrue(actual.contains(new Role(film, "Alex Parrish", new Actor("Priyanka", "Chopra"))));
	}

}
