package com.fidelity.advanced;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class DepartmentDaoTest {

	private AbstractApplicationContext factory;
	private DepartmentDao dao;

	@BeforeEach
	void setUp() {
		String springConfigurationFile = "advanced-beans.xml";
		factory = new ClassPathXmlApplicationContext(springConfigurationFile);
		dao = factory.getBean("dao", DepartmentDao.class);
	}
	
	@AfterEach
	void tearDown() {
		factory.close();
	}

	@Test
	void testDaoLifecycle() {
		assertEquals("INITIALIZED", dao.getStatus());
		dao.queryAllDepartments();
	}

}
