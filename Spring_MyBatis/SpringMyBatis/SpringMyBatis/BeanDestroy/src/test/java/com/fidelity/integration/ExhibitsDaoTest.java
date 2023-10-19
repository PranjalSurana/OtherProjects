package com.fidelity.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fidelity.business.Exhibit;

class ExhibitsDaoTest {
	private AbstractApplicationContext ctx;
	private ExhibitsDao dao;

	@BeforeEach
	void setUp(){
		ctx = new ClassPathXmlApplicationContext("museum-beans.xml");

		// TODO: Tell the application context to register a shut down hook
		ctx.registerShutdownHook();


		// Get an exhibitsDAO from the context
		dao = ctx.getBean("exhibitsDao", ExhibitsDao.class);
	}

	@AfterEach
	void tearDown(){
		// TODO: No need to call dao.close() explicitly if we have defined the bean correctly
//		dao.close();
		ctx.close();
		Logger logger = Logger.getLogger(ExhibitsDao.class.getName());
		logger.info("Spring context closed.");
	}

	@Test
	void testGetExhibits() {
		List<Exhibit> exhibits = dao.getExhibits();
		assertNotNull(exhibits);
		assertTrue(exhibits.size() > 0 );
		exhibits.forEach(System.out::println);
	}
}
