package com.fidelity.libraryservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fidelity.restcontroller.LibraryController;

@SpringBootTest(classes = { LibraryServiceApplication.class })
class LibraryServiceApplicationTests {
	@Autowired 
	LibraryController service;
	
	@Test
	void contextLoads() {
	}

	@Test 
	void testServiceIsAutowired() {
		assertNotNull(service);
	}
}
