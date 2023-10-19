package com.fidelity.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fidelity.business.Exhibit;

class ExhibitDaoTest {
	static ExhibitDaoMyBatisImpl dao;
	static ExhibitDaoMyBatisImpl dao2;
	static ExhibitDaoMyBatisImpl dao3;
	static String configFile = "museum-beans.xml";
	static String configFile2 = "hsqldbNoSchema.xml";
	static ClassPathXmlApplicationContext context;
	static ClassPathXmlApplicationContext context2;

	@BeforeAll
	static void setUp() throws Exception {
		// get the dao from Spring
		context = new ClassPathXmlApplicationContext(configFile);
		dao = context.getBean("exhibitsDao", ExhibitDaoMyBatisImpl.class);		
		dao2 = context.getBean("exhibitsDao", ExhibitDaoMyBatisImpl.class);		

		context2 = new ClassPathXmlApplicationContext(configFile2);
		dao3 = context2.getBean("exhibitsDao", ExhibitDaoMyBatisImpl.class);		

	}

	@AfterAll
	static void cleanUp() {
		context.close();
		context2.close();
	}

	@Test
	void testGetAllExhibits() throws InterruptedException {
		int repetitions = 3;
		int sleeptime = 1000;
		List<Exhibit> exhibits = null;

		System.out.println(".... First Dao .....");

		for (int i = 0; i < repetitions; i++) {		
			System.out.println("Request #" + (i+1));
			exhibits = dao.getAllExhibits();
			Thread.sleep(sleeptime);
		}

		System.out.println(".... Second Dao, same Spring Context.....");

		for (int i = 0; i < repetitions; i++) {				
			System.out.println("Request #" + (i+1));
			exhibits = dao2.getAllExhibits();
			Thread.sleep(sleeptime);
		}
		System.out.println(".... Third Dao, different Spring Context .....");

		for (int i = 0; i < repetitions; i++) {				
			System.out.println("Request #" + (i+1));
			exhibits = dao3.getAllExhibits();
			Thread.sleep(sleeptime);
		}

		assertNotNull(exhibits);

		assertTrue(exhibits.size() > 0);

		exhibits.forEach(System.out::println);
	}

}
