package com.fidelity.restcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;
import static org.springframework.test.jdbc.JdbcTestUtils.deleteFromTables;
import static org.springframework.test.jdbc.JdbcTestUtils.dropTables;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import com.fidelity.business.Widget;

/**
 * These test cases assert the behavior of our deployed service. To do that we will
 * start the application up and listen for a connection like it would do in
 * production, and then send an HTTP request and assert the response.
 * 
 * Note that Spring Boot has provided a TestRestTemplate for you automatically.
 * All you have to do is @Autowired it.
 * 
 * Instead of using a specific port number when constructing the server URL, simply use a 
 * relative URL. For example:
 * 	   String requestUrl = "/warehouse/widgets";
 * 
 * By using webEnvironment = WebEnvironment.RANDOM_PORT in SpringBootTest, a random port 
 * will be selected and added to the URL automatically.
 * The port that is being used appears in the Console window when the application is run.
 *
 * Also note the use of @Sql on the class to execute the database setup scripts before
 * each test case. Because @SpringBootTest runs Tomcat in a different thread than the
 * test cases themselves, @Transactional has no effect here. So we need to re-initialize 
 * the database before each test case.
 * Just another reason not to use the production database in testing :)
 * 
 * The database scripts referenced in @Sql are in the folder src/test/resources
 * 
 * For some test cases we'll need to query or modify the database directly, so we'll
 * use Spring's JdbcTestUtils class, which has methods like countRowsInTable() and
 * deleteFromTables().
 *  
 * Note that Spring Boot needs to find an application class in order to scan
 * for components. The trivial class com.fidelity.TestApplication in src/test/java 
 * contains the @SpringBootApplication annotation, which triggers the component scan.
 * 
 * @author ROI Instructor
 */

// TODO: note the Spring annotations on the test class definition.
//       (no code changes necessary)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@Sql({"classpath:schema-dev.sql", "classpath:data-dev.sql"}) // SQL files are in src/test/resources
public class WarehouseControllerE2eTest {

	// TODO: note the fields for the TestRestTemplate and JdbcTemplate
	//       (no code changes required)
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;  // for executing SQL queries
	
	// TODO: review the following four test cases and be sure you understand them

	/**
	 * This test verifies the WarehouseController can query successfully for all the
	 * Widgets in the Warehouse.
	 */
	@Test
	public void testQueryForAllWidgets() {
		// ARRANGE
		Widget widget1 = new Widget(1, "Low Impact Widget", 12.99, 2, 3);
		Widget widget3 = new Widget(3, "High Impact Widget", 89.99, 10, 8);

		// get the row count from the Widgets table
		int widgetCount = countRowsInTable(jdbcTemplate, "widgets");
		
		String requestUrl = "/warehouse/widgets";
		
		// ACT
		// send a GET request and convert the JSON response to an array of Widgets
		ResponseEntity<Widget[]> response = 
			restTemplate.getForEntity(requestUrl, Widget[].class);
		
		// ASSERT
		// verify the response HTTP status is OK
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		// verify that the service returned all Widgets in the database
		Widget[] responseWidgets = response.getBody();
		assertEquals(widgetCount, responseWidgets.length); 
		
		// spot-check a few Widgets
		assertEquals(widget1, responseWidgets[0]);
		assertEquals(widget3, responseWidgets[2]);
	}

	/**
	 * This test verifies the WarehouseController successfully handles the case
	 * where there are no Widgets in the Warehouse.
	 */
	@Test
	public void testQueryForAllWidgets_NoWidgetsInDb() {
		// delete all rows from the Widgets table
		deleteFromTables(jdbcTemplate, "widgets");
		
		String requestUrl = "/warehouse/widgets";

		ResponseEntity<Widget[]> response = 
			restTemplate.getForEntity(requestUrl, Widget[].class);
		
		// verify the response HTTP status is 204 (NO_CONTENT)
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	/**
	 * This test verifies the WarehouseController successfully handles a
	 * database error.
	 */
	@Test
	public void testGetWidgetById_NoWidgetTable() {
		// drop the Widgets table to force a database exception
		dropTables(jdbcTemplate, "widgets");

		String request = "/warehouse/widgets/99";

		ResponseEntity<Widget> response = restTemplate.getForEntity(request, Widget.class);

		// verify the response HTTP status
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	/**
	 * This test verifies the WarehouseController can query successfully for one Widget.
	 */
	@Test
	public void testQueryForWidgetById() {
		Widget expectedWidget = new Widget(3, "High Impact Widget", 89.99, 10, 8);
		String requestUrl = "/warehouse/widgets/3";

		ResponseEntity<Widget> response = 
			restTemplate.getForEntity(requestUrl, Widget.class);
		
		// verify the response HTTP status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		// verify that the service returned the correct Widget
		Widget responseBody = response.getBody();
		assertEquals(expectedWidget, responseBody);
	}

	// TODO: write a test case that verifies the WarehouseController returns
	//       HTTP status 400 (Bad Request) for a request to query for a widget
	//       that is not present in the database.
	@Test
	public void testQueryForWidgetById_NotPresent() {
		String requestUrl = "/warehouse/widgets/39";
		ResponseEntity<Widget[]> response =
				restTemplate.getForEntity(requestUrl, Widget[].class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	// TODO: write a test case that verifies the WarehouseController can successfully 
	//       add a Widget to the Warehouse.
	// HINT: see slide 3-32
	@Test
	public void testAddWidgetToWarehouse() throws Exception {
		int rowsBeforeInsert = countRowsInTable(jdbcTemplate, "widgets");
		int id = 42;
		Widget widget = new Widget(id, "Test widget", 4.52, 20, 10);
		String requestUrl = "/warehouse/widgets";
		ResponseEntity<DatabaseRequestResult> response =
				restTemplate.postForEntity(requestUrl, widget, DatabaseRequestResult.class);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertEquals(1, response.getBody().getRowCount());
		int rowsAfterInsert = countRowsInTable(jdbcTemplate, "widgets");
		assertEquals(rowsBeforeInsert + 1, rowsAfterInsert);
		assertEquals(1, countRowsInTableWhere(jdbcTemplate, "widgets", "id = " + id));
	}

	// TODO: write a test case that verifies that a request to add a widget
	//       with a duplicate id does the following: 
	//       1. it gets a response with HTTP status 400 (Bad Request), and
	//       2. it gets a response whose body is a JSON object with a rowCount of 0, and
	//       3. it does not add a row to the Widgets table
	@Test
	public void testAddWidgetToWarehouse_DuplicateId() throws Exception {
		int rowsBeforeInsert = countRowsInTable(jdbcTemplate, "widgets");
		int id = 42;
		Widget widget = new Widget(id, "Test widget", 4.52, 20, 10);
		String requestUrl = "/warehouse/widgets";
		ResponseEntity<DatabaseRequestResult> response =
				restTemplate.postForEntity(requestUrl, widget, DatabaseRequestResult.class);
		ResponseEntity<DatabaseRequestResult> response2 =
				restTemplate.postForEntity(requestUrl, widget, DatabaseRequestResult.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response2.getStatusCode());
	}

	// the remaining test cases test different scenarios for the RESTful service

	@Test
	public void testUpdateWidgetInWarehouse() throws Exception {
		// ARRANGE
		int oldWidgetCount = countRowsInTable(jdbcTemplate, "widgets");
		int id = 1;
		Widget updatedWidget = new Widget(id, "New Description", 9.99, 9, 19);

		// ACT
		// TestRestTemplate doesn't define a putForEntity() method, so we need to create a
		// RequestEntity instance and then pass it to the TestRestTemplate exchange() method
		String requestUrl = "/warehouse/widgets";
		RequestEntity<Widget> requestEntity = 
			RequestEntity.put(new URI(requestUrl)) // returns RequestEntity.BodyBuilder
						 .contentType(MediaType.APPLICATION_JSON) 
						 .accept(MediaType.APPLICATION_JSON)
						 .body(updatedWidget);

		ResponseEntity<DatabaseRequestResult> response = 
			restTemplate.exchange(requestEntity, DatabaseRequestResult.class);

		// ASSERT
		// verify the response HTTP status and response body
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		DatabaseRequestResult responseDto = response.getBody(); // {"rowCount": 1}
		assertEquals(1, responseDto.getRowCount());

		// verify that no rows were added to the Widgets table
		int newWidgetCount = countRowsInTable(jdbcTemplate, "widgets");
		assertEquals(oldWidgetCount, newWidgetCount);

		// verify that the widget was updated in the widgets table
		assertEquals(1, countRowsInTableWhere(jdbcTemplate, "widgets", """
					id = 1
				and description = 'New Description'
				and price = 9.99
				and gears = 9
				and sprockets = 19
			"""));
	}

	/**
	 * This test verifies the WarehouseController can successfully remove a Widget from
	 * the Warehouse.
	 */
	@Test
	public void testRemoveWidgetFromWarehouse() throws Exception{
		int widgetCount = countRowsInTable(jdbcTemplate, "widgets");

		int id = 1;
		String requestUrl = "/warehouse/widget/" + id;
		
		// TestRestTemplate doesn't define a deleteEntity() method, so we need to create a
		// RequestEntity instance and then pass it to the TestRestTemplate exchange() method
		RequestEntity<Void> requestEntity = 
			RequestEntity.delete(new URI(requestUrl))  // returns a RequestEntity.HeadersBuilder
						 .build();
		
		ResponseEntity<DatabaseRequestResult> response = 
				restTemplate.exchange(requestEntity, DatabaseRequestResult.class);
		
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		DatabaseRequestResult responseBody = response.getBody(); // {"rowCount": 1}
		assertEquals(1, responseBody.getRowCount());

		// verify that one row was deleted from the Widgets table
		int newWidgetCount = countRowsInTable(jdbcTemplate, "widgets");
		assertEquals(widgetCount - 1, newWidgetCount);
		
		// verify that the widget is no longer in the Widgets table
		assertEquals(countRowsInTableWhere(jdbcTemplate, "widgets", "id = " + id), 0);
	}
	
	/**
	 * This test verifies the WarehouseController will return the HTTP status
	 * NotFound when attempting to remove a Widget that is not in the Warehouse.
	 */
	@Test
	public void testRemoveWidgetFromWarehouse_NotPresent() throws Exception{
		int widgetCount = countRowsInTable(jdbcTemplate, "widgets");

		int id = 99;
		String requestUrl = "/warehouse/widget/" + id;
		
		RequestEntity<Void> requestEntity = 
			RequestEntity.delete(new URI(requestUrl)).build();
		
		ResponseEntity<DatabaseRequestResult> response = 
				restTemplate.exchange(requestEntity, DatabaseRequestResult.class);
		
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		// verify that no rows were deleted from the Widgets table
		assertEquals(countRowsInTable(jdbcTemplate, "widgets"), widgetCount);
	}

}

