package com.fidelity.business.service;

import com.fidelity.business.Widget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * WarehouseBusinessServiceIntegrationTest is an integration test for WarehouseBusinessServiceImpl.
 * 
 * Notice that the database schema and data scripts are run
 * after setting the DataSource. 
 * The database scripts are in the folder: src/test/resources/
 * This guarantees that the database is in a known state before the tests are run.
 *  
 * To verify that the DAO is actually working, we'll need to query the database 
 * directly, so we'll use Spring's JdbcTestUtils class, which has methods like 
 * countRowsInTable() and deleteFromTables().
 *
 * Notice the use of @Transactional to automatically rollback 
 * any changes to the database that may be made in a test.
 *
 * Note that Spring Boot needs to find an application class in order to scan
 * for components. The trivial class com.fidelity.TestApplication in src/test/java 
 * contains the @SpringBootApplication annotation, which triggers the component scan.
 * 
 * @author ROI Instructor
 *
 */

// TODO: add the necessary Spring annotations to:
//       1. define this class as a Spring Boot test
//       2. enable automatic transaction management so database changes 
//          are rolled back after every test case
// HINT: See slide 3-20

@SpringBootTest
@Transactional
class WarehouseBusinessServiceIntegrationTest {
	// TODO: add an autowired field for the business service being tested

	@Autowired
	WarehouseBusinessService warehouseBusinessService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;  // for executing SQL queries
	
	// TODO: define a List of all widgets in the database

	List<Widget> widgetList;

	Widget widget1 = new Widget(1, "Low Impact Widget", 12.99, 2, 3);
	Widget widget2 = new Widget(2, "Medium Impact Widget", 42.99, 5, 5);
	Widget widget3 = new Widget(3, "High Impact Widget", 89.99, 10, 8);

	@BeforeEach
	void setUp() {
		widgetList = new ArrayList<Widget>();
		widgetList.add(widget1);
		widgetList.add(widget2);
		widgetList.add(widget3);
	}
	// Because the test database is tiny, we can check all products.
	// If the database was larger, we could just spot-check a few products.
	
	// TODO: write integration tests to verify the operation of the
	//       WarehouseBusinessService methods:
	//			findAllWidgets()
	//			findWidgetById()
	//			removeWidget()
	//			addWidget()
	//			modifyWidget()
	// HINT: the tests will be similar to the DAO integration test cases
	
	@Test
	void testGetAllWidgets() {
		List<Widget> widgets = warehouseBusinessService.findAllWidgets();
		assertEquals(widgetList, widgets);
	}

	@Test
	void testFindWidgetById() {
		Widget widget = warehouseBusinessService.findWidgetById(1);
		assertEquals(widget, widget1);
	}

	@Test
	void testDeleteWidget() {
		int deletedWidgetId = warehouseBusinessService.removeWidget(2);
		assertEquals(1, deletedWidgetId);
	}
	
	@Test
	void testInsertWidget() {
		Widget widget4 = new Widget(4, "Low Impact Widget", 56.99, 12, 3);
		int rowCount = warehouseBusinessService.addWidget(widget4);
		assertEquals(1, rowCount);
	}
	
	@Test
	void testUpdateWidget() {
		Widget widget4 = new Widget(3, "Very High Impact Widget", 56.99, 12, 3);
		int rowCount = warehouseBusinessService.modifyWidget(widget4);
		assertEquals(1, rowCount);
		assertEquals("Very High Impact Widget", widget4.getDescription());
	}

	// ***** Utility Method to Load a Widget from the Database *****

	private Widget loadWidgetFromDb(int id) {
		String sql = "select * from widgets where id = " + id;
		
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> 
			new Widget(rs.getInt("id"), rs.getString("description"), rs.getDouble("price"), 
					   rs.getInt("gears"), rs.getInt("sprockets")));
	}

}
