@SpringBootTest
@Transactional
public class XYZBusinessServiceIntegrationTest {

    @Autowired
    private XYZBusinessService xyzBusinessService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Define sample list for X
    // Define sample data

    @BeforeEach
    void setUp() {
        xyzList = new ArrayList<>();
        xyzList.add(xyz1);
        xyzList.add(xyz2);
        xyzList.add(xyz3);
    }

    @Test
    void testGetAllWidgets() {
        List<XYZ> xyzs = xyzBusinessService.findAllXYZs();
        assertFalse(xyzs.isEmpty());
    }

    @Test
    void testGetAllWidgets_EmptyTable() {
        deleteFromTables(jdbcTemplate, "TableName");
        List<XYZ> xyzs = xyzBusinessService.findAllXYZs();
        assertTrue(xyzs.isEmpty());
    }

    @Test
    void testFindWidgetById() {
        XYZ xyz = xyzBusinessService.findXYZById(1);
        assertEquals(xyz, xyz1);
    }

    @Test
    void testFindWidgetById_ZeroId() {
        assertThrows(IllegalArgumentException.class, () -> {
            xyzBusinessService.findXYZById(0);
        });
    }

    @Test
    void testFindWidgetById_NegativeId() {
        assertThrows(IllegalArgumentException.class, () -> {
            xyzBusinessService.findXYZById(-1);
        });
    }

    @Test
    void testFindWidgetByName() {
        String captainName = xyzBusinessService.findCaptainByXYZName("RMS Titanic");
        assertEquals(captainName, xyz1.getCaptain());
    }

    @Test
    void testFindWidgetByName_EmptyString() {
        assertThrows(IllegalArgumentException.class, () -> {
            xyzBusinessService.findCaptainByXYZName("");
        });
    }

    @Test
    void testFindWidgetByName_NullString() {
        assertThrows(IllegalArgumentException.class, () -> {
            xyzBusinessService.findCaptainByXYZName(null);
        });
    }


}