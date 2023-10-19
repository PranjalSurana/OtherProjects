public class XYZControllerPojoUnitTest {

    @Mock
    private XYZDao xyzDao;

    @InjectMocks
    private XYZServiceImpl xyzService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllXYZs() {
        List<XYZ> allXYZs = Arrays.asList(
                new XYZ(1,"f1","l1",1789,1797,"img1.jpg","bio1"),
                new XYZ(2, "f2", "l2", 1989, 2000, "img2.jpg", "bio2"),
                new XYZ(3, "f3", "l3", 1889, 1930, "img3.jpg", "bio3")
        );
        when(xyzDao.getAllXYZs()).thenReturn(allXYZs);
        assertEquals(allXYZs, xyzService.findAllXYZs());
    }

    @Test
    void testFindAllXYZs_DaoReturnsEmptyList() {
        when(xyzDao.getAllXYZs()).thenReturn(new ArrayList<XYZ>());
        assertEquals(new ArrayList<XYZ>(), xyzService.findAllXYZs());
    }

    @Test
    void testFindAllXYZs_DaoThrowsException() {
        // Configure the mock DAO to throw an exception when getAllXYZs is called
        when(xyzDao.getAllXYZs())
                .thenThrow(new RuntimeException("mock DAO exception"));

        assertThrows(XYZDatabaseException.class, () -> {
            xyzService.findAllXYZs();
        });
    }

    @Test
    void testFindXYZById() {
        int id = 1;
        XYZ firstXYZ = new XYZ(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(xyzDao.getXYZ(id))
                .thenReturn(firstXYZ);

        XYZ xyz = xyzService.findXYZById(id);

        assertEquals(firstXYZ, xyz);
    }

    @Test
    void testFindXYZById_IdNotExists() {
        when(xyzDao.getXYZ(1000))
                .thenThrow(new RuntimeException("mock DAO exception"));

        assertThrows(XYZDatabaseException.class, () -> {
            xyzService.findXYZById(1);
        });
    }

    @Test
    void testAddXYZ() {
        XYZ xyz = new XYZ(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(xyzDao.insertXYZ(xyz))
                .thenReturn(1);

        int rowsInserted = xyzService.addXYZ(xyz);

        assertEquals(1, rowsInserted);
    }

    @Test
    void testAddXYZ_DaoThrowsWarehouseDatabaseException() {
        int id = 1;
        XYZ xyz = new XYZ(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(xyzDao.insertXYZ(xyz))
                .thenThrow(new XYZDatabaseException("mock DAO exception"));

        assertThrows(XYZDatabaseException.class, () -> {
            xyzService.addXYZ(xyz);
        });
    }

    @Test
    void testAddXYZ_DaoThrowsDuplicateKeyException() {
        int id = 1;
        XYZ xyz = new XYZ(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(xyzDao.insertXYZ(xyz))
                .thenThrow(new DuplicateKeyException("mock DAO exception"));

        assertThrows(DuplicateKeyException.class, () -> {
            xyzService.addXYZ(xyz);
        });
    }

    @Test
    void testAddXYZ_NullXYZ() {
        assertThrows(IllegalArgumentException.class, () -> {
            xyzService.addXYZ(null);
        });
    }

    @Test
    void testAddXYZ_XYZMissingBio() {
        XYZ xyz = new XYZ(1,"f1","l1",1789,1797,"img1.jpg","");

        assertThrows(IllegalArgumentException.class, () -> {
            xyzService.addXYZ(xyz);
        });
    }

    @Test
    void testUpdateXYZ() {
        XYZ originalXYZ = new XYZ(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(xyzDao.updateXYZ(originalXYZ))
                .thenReturn(1);

        int count = xyzService.modifyXYZ(originalXYZ);

        assertEquals(1, count);
    }

    @Test
    void testUpdateXYZ_IdNotExists() {
        XYZ xyz = new XYZ(10, "f", "l", 1973, 1988, "i.img", "b1");
        when(xyzDao.updateXYZ(xyz))
                .thenThrow(new RuntimeException("mock DAO exception"));

        assertThrows(XYZDatabaseException.class, () -> {
            xyzService.modifyXYZ(xyz);
        });
    }

    @Test
    void testDeleteXYZ() {
        int id = 1;
        when(xyzDao.deleteXYZ(id))
                .thenReturn(1);

        int count = xyzService.removeXYZ(id);

        assertEquals(1, count);
    }

    @Test
    void testDeleteXYZ_IdNotExists() {
        when(xyzDao.deleteXYZ(10))
                .thenThrow(new RuntimeException("mock DAO exception"));

        assertThrows(XYZDatabaseException.class, () -> {
            xyzService.removeXYZ(10);
        });
    }

}