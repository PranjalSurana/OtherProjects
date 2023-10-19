package com.fidelity.dependency;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;


class FilmTest {
	private AbstractApplicationContext factory;
	private Film film;

	@BeforeEach
	void setUp() {
		factory = new AnnotationConfigApplicationContext(AppConfig.class);
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
		assertEquals(5, actual.size());
		assertTrue(actual.contains(new Actor("Priyanka", "Chopra")));
		assertTrue(actual.contains(new Actor("Johnny", "Depp")));
	}
}
