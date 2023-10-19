package com.fidelity.restcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;
import static org.springframework.test.jdbc.JdbcTestUtils.deleteFromTables;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fidelity.business.Gadget;
import com.fidelity.business.Widget;
import com.fidelity.dbtestutils.DatabaseTestSupport;
import com.fidelity.warehouseservice.WarehouseServiceApplication;

/**
 * These test cases assert the behavior of our deployed service. To do that we
 * will start the application up and listen for a connection like it would do in
 * production, and then send an HTTP request and assert the response.
 * 
 * Note that Spring Boot has provided a TestRestTemplate for you automatically.
 * All you have to do is @Autowired it.
 * 
 * Instead of using a specific port number when constructing the server URL,
 * simply use a relative URL. For example: String request =
 * "/warehouse/widgets";
 * 
 * By using webEnvironment = WebEnvironment.RANDOM_PORT in SpringBootTest, a
 * random port will be selected and added to the URL automatically. The port
 * that is being used appears in the Console window when the application is run.
 *
 * Also note the use of @Sql on the class to execute the database setup scripts
 * before each test case. Because @SpringBootTest runs Tomcat in a different
 * thread than the test cases themselves, @Transactional has no effect here. So
 * we need to re-initialize the database before each test case. Just another
 * reason not to use the production database in testing :)
 * 
 * The database scripts referenced in @Sql are in the folder src/test/resources
 * 
 * For some test cases we'll need to query or modify the database directly, so
 * we'll use Spring's JdbcTestUtils class, which has methods like
 * countRowsInTable() and deleteFromTables().
 * 
 * Note that Spring Boot needs to find an application class in order to scan for
 * components. The trivial class com.fidelity.TestApplication in src/test/java
 * contains the @SpringBootApplication annotation, which triggers the component
 * scan.
 * 
 * @author ROI Instructor
 */

@SpringBootTest(classes = WarehouseServiceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
//@Sql(scripts={"classpath:schema-dev.sql", "classpath:data-dev.sql"},
//     executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD) 
public class WarehouseControllerE2eTest {
	@Autowired
	private DataSource datasource;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate; // for executing SQL queries

	@Autowired
	private Environment env;

	// Path to database scripts folder
	private Path path = Paths.get("src", "test", "resources");

	// In memory database scripts for dev
	private String schemasql = "schema-dev.sql";
	private String datasql = "data-dev.sql";

	// Oracle database scripts for test (scott)
	private String schemasqlOracle = "schemaOracle.sql";
	private String datasqlOracle = "dataOracle.sql";

	// Builds a map of database script files
	// Key: profile name
	// Value: array of PathResources for schema and data sql scripts
	private Map<String, PathResource[]> initializeScriptsMap() {
		PathResource[] inmemoryscripts = { new PathResource(path.resolve(schemasql)),
										   new PathResource(path.resolve(datasql)) };
		PathResource[] oraclescripts   = { new PathResource(path.resolve(schemasqlOracle)),
										   new PathResource(path.resolve(datasqlOracle)) };

		Map<String, PathResource[]> sqlscriptsmap = new HashMap<>();
		sqlscriptsmap.put("prod", null); // Do NOT run tests against the production database
		sqlscriptsmap.put("dev",  inmemoryscripts);
		sqlscriptsmap.put("test", oraclescripts);

		return sqlscriptsmap;
	}

	@BeforeEach
	public void setup() throws SQLException {
		// The database tables
		String [] tables = { "widgets", "gadgets" };
		
		// Find the active profile
		String profile = env.getProperty("spring.profiles.active");

		Map<String, PathResource[]> sqlscriptsmap = initializeScriptsMap();

		DatabaseTestSupport support = new DatabaseTestSupport(jdbcTemplate);
		support.executeScripts(datasource, sqlscriptsmap.get(profile), tables);
	}

	// **** Widget Tests ****
	/**
	 * This test verifies the WarehouseController can query successfully for all the
	 * Widgets in the Warehouse.
	 */
	@Test
	public void testQueryForAllWidgets() {
		// get the row count from the Widgets table
		int widgetCount = countRowsInTable(jdbcTemplate, "widgets");

		String request = "/warehouse/widgets";

		ResponseEntity<Widget[]> response = restTemplate.getForEntity(request, Widget[].class);

		// verify the response HTTP status is OK
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// verify that the service returned all Widgets in the database
		Widget[] responseWidgets = response.getBody();
		assertEquals(widgetCount, responseWidgets.length);

		// spot-check a few Widgets
		assertEquals(new Widget(1, "Low Impact Widget", 12.99, 2, 3), responseWidgets[0]);
		assertEquals(new Widget(3, "High Impact Widget", 89.99, 10, 8), responseWidgets[2]);
	}

	/**
	 * This test verifies the WarehouseController successfully handles the case where
	 * there are no Widgets in the Warehouse.
	 */
	@Test
	public void testQueryForAllWidgets_NoWidgetsInDb() {
		// delete all rows from the Widgets table
		deleteFromTables(jdbcTemplate, "widgets");

		String request = "/warehouse/widgets";

		ResponseEntity<Widget[]> response = 
				restTemplate.getForEntity(request, Widget[].class);

		// verify the response HTTP status is 204 (NO_CONTENT)
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	/**
	 * This test verifies the WarehouseController can query successfully for one
	 * Widget.
	 */
	@Test
	public void testQueryForWidgetById() {
		String request = "/warehouse/widgets/3";

		ResponseEntity<Widget> response = restTemplate.getForEntity(request, Widget.class);

		// verify the response HTTP status
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// verify that the service returned the correct Widget
		assertEquals(new Widget(3, "High Impact Widget", 89.99, 10, 8), response.getBody());
	}

	/**
	 * This test verifies the WarehouseController handles a query for a non-existent
	 * Widget.
	 */
	@Test
	public void testQueryForWidgetById_NotPresent() {
		String request = "/warehouse/widgets/99";

		ResponseEntity<Widget> response = restTemplate.getForEntity(request, Widget.class);

		// verify the response HTTP status
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	/**
	 * This test verifies the WarehouseController can successfully add a Widget to the
	 * Warehouse.
	 */
	@Test
	public void testAddWidgetToWarehouse() throws Exception {
		int widgetCount = countRowsInTable(jdbcTemplate, "widgets");

		int id = 42;
		Widget w = new Widget(id, "Test widget", 4.52, 20, 10);

		String request = "/warehouse/widgets";

		ResponseEntity<DatabaseRequestResult> response = restTemplate.postForEntity(request, w,
				DatabaseRequestResult.class);

		// verify the response HTTP status and response body
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().getRowCount()); // {"rowCount": 1}

		// verify that one row was added to the Widgets table
		int newWidgetCount = countRowsInTable(jdbcTemplate, "widgets");
		assertEquals(widgetCount + 1, newWidgetCount);

		// verify that the new widget is in the Widgets table
		assertEquals(1, countRowsInTableWhere(jdbcTemplate, "widgets", "id = " + id));
	}

	/**
	 * This test verifies the WarehouseController can successfully remove a Widget from
	 * the Warehouse.
	 */
	@Test
	public void testRemoveWidgetFromWarehouse() throws Exception {
		int widgetCount = countRowsInTable(jdbcTemplate, "widgets");

		int id = 1;
		String request = "/warehouse/widgets/" + id;

		RequestEntity<Void> requestEntity = RequestEntity.delete(new URI(request)).build();

		ResponseEntity<DatabaseRequestResult> response = restTemplate.exchange(requestEntity,
				DatabaseRequestResult.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().getRowCount()); // {"rowCount": 1}

		// verify that one row was deleted from the Widgets table
		int newWidgetCount = countRowsInTable(jdbcTemplate, "widgets");
		assertEquals(widgetCount - 1, newWidgetCount);

		// verify that the widget is no longer in the Widgets table
		assertEquals(0, countRowsInTableWhere(jdbcTemplate, "widgets", "id = " + id));
	}

	/**
	 * This test verifies the WarehouseController will return the HTTP status NotFound
	 * when attempting to remove a Widget that is not in the Warehouse.
	 */
	@Test
	public void testRemoveWidgetFromWarehouse_NotPresent() throws Exception {
		int widgetCount = countRowsInTable(jdbcTemplate, "widgets");

		int id = 99;
		String request = "/warehouse/widgets/" + id;

		RequestEntity<Void> requestEntity = RequestEntity.delete(new URI(request)).build();

		ResponseEntity<DatabaseRequestResult> response = restTemplate.exchange(requestEntity,
				DatabaseRequestResult.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(0, response.getBody().getRowCount()); // {"rowCount": 0}

		// verify that no rows were deleted from the Widgets table
		assertEquals(widgetCount, countRowsInTable(jdbcTemplate, "widgets"));
	}

	// **** Gadget Tests ****

	/**
	 * This test verifies the WarehouseController can query successfully for all the
	 * Gadgets in the Warehouse.
	 */
	@Test
	public void testQueryForAllGadgets() {
		// get the row count from the Gadgets table
		int gadgetCount = countRowsInTable(jdbcTemplate, "gadgets");

		String request = "/warehouse/gadgets";

		ResponseEntity<Gadget[]> response = restTemplate.getForEntity(request, Gadget[].class);

		// verify the response HTTP status
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// verify that the service returned all Gadgets in the database
		Gadget[] responseGadgets = response.getBody();
		assertEquals(gadgetCount, responseGadgets.length);

		// spot-check a few Gadgets
		assertEquals(responseGadgets[0], new Gadget(1, "Two Cylinder Gadget", 19.99, 2));
		assertEquals(responseGadgets[2], new Gadget(3, "Eight Cylinder Gadget", 49.99, 8));
	}

	/**
	 * This test verifies the WarehouseController successfully handles the case where
	 * there are no Gadgets in the Warehouse.
	 */
	@Test
	public void testQueryForAllGadgets_NoGadgetsInDb() {
		// delete all rows from the Gadgets table
		deleteFromTables(jdbcTemplate, "gadgets");

		String request = "/warehouse/gadgets";

		ResponseEntity<Gadget[]> response = 
				restTemplate.getForEntity(request, Gadget[].class);

		// verify the response HTTP status is 204
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	/**
	 * This test verifies the WarehouseController can query successfully for one
	 * Gadget.
	 */
	@Test
	public void testQueryForGadgetById() {
		String request = "/warehouse/gadgets/3";

		ResponseEntity<Gadget> response = restTemplate.getForEntity(request, Gadget.class);

		// verify the response HTTP status
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// verify that the service returned the correct Gadget
		assertEquals(response.getBody(), new Gadget(3, "Eight Cylinder Gadget", 49.99, 8));
	}

	/**
	 * This test verifies the WarehouseController handles a query for a non-existent
	 * Gadget.
	 */
	@Test
	public void testQueryForGadgetById_NotPresent() {
		String request = "/warehouse/gadgets/99";

		ResponseEntity<Gadget> response = restTemplate.getForEntity(request, Gadget.class);

		// verify the response HTTP status
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/**
	 * This test verifies the WarehouseController can successfully add a Gadget to the
	 * Warehouse.
	 */
	@Test
	public void testAddGadgetToWarehouse() throws Exception {
		String tableName = "gadgets";
		int gadgetCount = countRowsInTable(jdbcTemplate, tableName);

		int id = 42;
		Gadget w = new Gadget(42, "Test Gadget", 19.99, 2);

		String request = "/warehouse/gadgets";

		ResponseEntity<DatabaseRequestResult> response = restTemplate.postForEntity(request, w,
				DatabaseRequestResult.class);

		// verify the response HTTP status and response body
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().getRowCount()); // {"rowCount": 1}

		// verify that one row was added to the Gadgets table
		int newWidgetCount = countRowsInTable(jdbcTemplate, tableName);
		assertEquals(gadgetCount + 1, newWidgetCount);

		// verify that the new Gadget is in the Gadgets table
		assertEquals(1, countRowsInTableWhere(jdbcTemplate, tableName, "id = " + id));
	}

	/**
	 * This test verifies the WarehouseController can successfully remove a Gadget from
	 * the Warehouse.
	 */
	@Test
	public void testRemoveGadgetFromWarehouse() throws Exception {
		int gadgetCount = countRowsInTable(jdbcTemplate, "gadgets");

		int id = 1;
		String request = "/warehouse/gadgets/" + id;

		RequestEntity<Void> requestEntity = RequestEntity.delete(new URI(request)).build();

		ResponseEntity<DatabaseRequestResult> response = restTemplate.exchange(requestEntity,
				DatabaseRequestResult.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().getRowCount()); // {"rowCount": 1}

		// verify that one row was deleted from the Gadgets table
		int newWidgetCount = countRowsInTable(jdbcTemplate, "gadgets");
		assertEquals(gadgetCount - 1, newWidgetCount);

		// verify that the gadget is no longer in the Gadgets table
		assertEquals(0, countRowsInTableWhere(jdbcTemplate, "gadgets", "id = " + id));
	}

	/**
	 * This test verifies the WarehouseController will return the HTTP status NotFound
	 * when attempting to remove a Gadget that is not in the Warehouse.
	 */
	@Test
	public void testRemoveGadgetFromWarehouse_NotPresent() throws Exception {
		int gadgetCount = countRowsInTable(jdbcTemplate, "gadgets");

		int id = 99;
		String request = "/warehouse/gadgets/" + id;

		RequestEntity<Void> requestEntity = RequestEntity.delete(new URI(request)).build();

		ResponseEntity<DatabaseRequestResult> response = restTemplate.exchange(requestEntity,
				DatabaseRequestResult.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(0, response.getBody().getRowCount()); // {"rowCount": 0}

		// verify that no rows were deleted from the Gadgets table
		assertEquals(gadgetCount, countRowsInTable(jdbcTemplate, "gadgets"));
	}

}
