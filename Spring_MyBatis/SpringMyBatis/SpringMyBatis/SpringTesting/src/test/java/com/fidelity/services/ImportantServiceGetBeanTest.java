package com.fidelity.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class ImportantServiceGetBeanTest {
	private ImportantService service;
	private AbstractApplicationContext context;

	@BeforeEach
	void setUp() {
		context = new ClassPathXmlApplicationContext("classpath:beans.xml");
		service = context.getBean(ImportantService.class);
	}
	
	@AfterEach
	void tearDown() {
		context.close();
	}

	@Test
	void testService() {
		assertEquals("message", service.provide());
	}

}
