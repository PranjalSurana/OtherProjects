package com.fidelity.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleDataSourceTest {
	private SimpleDataSource dataSource;
	
	@BeforeEach
	void setUp() throws Exception {
		dataSource = new SimpleDataSource();
	}

	@AfterEach
	void tearDown() throws Exception {
		dataSource.shutdown();
		dataSource = null;
	}

	@Test
	void testDataSourceCreated() {
		assertNotNull(dataSource);
	}

	@Test
	void getConnectionReturnsNonNullConnectionObject() {
		SimpleDataSource simpleDataSource = new SimpleDataSource();
		assertNotNull(simpleDataSource.getConnection());
	}

}
