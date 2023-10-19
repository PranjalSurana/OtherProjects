package com.fidelity.dependency;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


class FilmTest {
	private AbstractApplicationContext factory;
	private Film film;

	@BeforeEach
	void setUp() {
		String springConfigurationFile = "dependency-beans.xml";
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
		List<Actor> actual = film.getCast();
		assertEquals(3, actual.size());
		assertTrue(actual.contains(new Actor("Priyanka", "Chopra")));
		assertFalse(actual.contains(new Actor("Johnny", "Depp")));
	}
	
	@Test
	void testCastMap() {
		FilmWithMap film = factory.getBean("no-way-jose", FilmWithMap.class);
		Map<String, Actor> actual = film.getCast();
		assertEquals(3, actual.size());
		assertEquals(new Actor("Priyanka", "Chopra"), actual.get("Alex Parrish"));
	}
}
