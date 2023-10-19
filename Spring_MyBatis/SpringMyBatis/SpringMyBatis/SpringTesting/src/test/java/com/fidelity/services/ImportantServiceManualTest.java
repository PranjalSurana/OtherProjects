package com.fidelity.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class ImportantServiceManualTest {
	private ImportantService service;

	@BeforeEach
	void setUp() {
		StringProvider sp = new StringProviderImpl();
		service = new ImportantService();
		ReflectionTestUtils.setField(service, "sp", sp);
	}

	@Test
	void testService() {
		assertEquals("message", service.provide());
	}

}
