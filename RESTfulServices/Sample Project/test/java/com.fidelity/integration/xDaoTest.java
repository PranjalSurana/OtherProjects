// DaoTest is an integration test for ShipDaoMyBatisImpl.

@SpringBootTest
@Transactional
class xyzDaoTest {

    @Autowired
    private xyzDao xyzDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Create objects of type XYZ

    @Test
    void testQueryAllXYZs_Success() {
        List<XYZ> xyzs = xyzDao.queryAllXYZs();
        assertTrue(!xyzs.isEmpty());
        assertEquals(xyz1, xyzs.get(0));
        assertEquals(xyz2, xyzs.get(1));
        assertEquals(xyz3, xyzs.get(2));
    }

    @Test
    void testQueryAllXYZs_XYZsTableIsEmpty() {
        deleteFromTables(jdbcTemplate, "xyzTableName");
        List<XYZ> xyzs = xyzDao.queryAllShips();
        assertEquals(new ArrayList<XYZ>(), xyzs);
    }

    @Test
    void testQueryXYZById_Present() {
        XYZ xyz = xyzDao.queryXYZById(1);
        assertEquals(xyz1, xyz);
    }

    @Test
    void testQueryXYZById_NotPresent() {
        XYZ xyz = xyzDao.queryXYZById(20000);
        assertNull(xyz);
    }

    @Test
    void testQueryXYZById_ZeroId() {
        XYZ xyz = xyzDao.queryXYZById(0);
        assertNull(xyz);
    }

    @Test
    void testQueryXYZById_NegativeId() {
        XYZ xyz = xyzDao.queryXYZById(-1);
        assertNull(xyz);
    }

}