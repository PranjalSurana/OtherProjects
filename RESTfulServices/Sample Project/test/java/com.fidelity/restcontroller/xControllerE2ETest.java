@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"classpath:schema.sql", "classpath:data.sql"})
public class XYZControllerE2eTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Define Mock data for XYZ

    @Test
    public void testQueryForAllXYZs() {
        int xyzCount = countRowsInTable(jdbcTemplate, "TableName");
        String requestUrl = "";
        ResponseEntity<XYZ[]> response = restTemplate.getForEntity(requestUrl, XYZ[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        XYZ[] responseXYZs = response.getBody();
        assertEquals(xyzCount, responseXYZs.length);
        assertEquals(xyz1, responseXYZs[0]);
        assertEquals(xyz3, responseXYZs[2]);
    }

    @Test
    public void testQueryForAllXYZs_NoXYZsInDb() {
        deleteFromTables(jdbcTemplate, "TableName");
        String requestUrl = "";
        ResponseEntity<XYZ[]> response = restTemplate.getForEntity(requestUrl, XYZ[].class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue(response.getBody() == null || response.getBody().isBlank());
    }

    @Test
    public void testQueryForXYZById() {
        String requestUrl = "";
        ResponseEntity<XYZ> response = restTemplate.getForEntity(requestUrl, XYZ.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        XYZ responseBody = response.getBody();
        assertEquals(xyz3, responseBody);
    }

    @Test
    public void testQueryForXYZById_NotPresent() {
        String requestUrl = "";
        ResponseEntity<XYZ[]> response = restTemplate.getForEntity(requestUrl, XYZ[].class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}