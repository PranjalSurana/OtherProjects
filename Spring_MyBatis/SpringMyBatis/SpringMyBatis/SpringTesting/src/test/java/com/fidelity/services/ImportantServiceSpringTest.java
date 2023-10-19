package com.fidelity.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:beans.xml")
class ImportantServiceSpringTest {
	@Autowired
	private ImportantService service;

	@Test
	void testService() {
		assertEquals("message", service.provide());
	}

}
