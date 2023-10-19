@WebMvcTest
public class XYZControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private XYZBusinessService mockXYZBusinessService;

    // Declare List of XYZ
    // Declare Mock Data

    @Autowired
    ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        xyzList = new ArrayList<>();
        xyzList.add(xyz1);
        xyzList.add(xyz2);
        xyzList.add(xyz3);
    }

    @Test
    public void contextLoads() {
        assertNotNull(applicationContext);
    }

    @Test
    public void testPingEndpoint() throws Exception {
        mockMvc.perform(get("/xyzs/ping").accept(MediaType.ALL_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testQueryForAllXYZs() throws Exception {
        when(mockXYZBusinessService.findAllXYZs()).thenReturn(xyzList);
        mockMvc.perform(get("/xyzs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("RMS Titanic"))
                .andExpect(jsonPath("$[2].captain").value("James T. Kirk"));
    }

    @Test
    public void testQueryForAllXYZs_DaoReturnsEmptyList() throws Exception {
        when(mockXYZBusinessService.findAllXYZs())
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/xyzs"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(is(emptyOrNullString())));
    }

    // -----------------------------------------

    // @Test
    // public void testQueryForCaptainByShipName() throws Exception {
    //     String name = "RMS Titanic";
    //     String captain = "Captain Edward J. Smith";


    //     when(mockBusinessService.findCaptainByShipName(name))
    //             .thenReturn(captain);

    //     mockMvc.perform(get("/ships/captain/" + name))
    //             .andDo(print())
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.shipCaptain").value(captain));

    // }

    // @Test
    // public void testQueryForGetCaptainInvalidShipName() throws Exception {
    //     String name = "";

    //     mockMvc.perform(get("/ships/captain/" + name))
    //             .andDo(print())
    //             .andExpect(status().isBadRequest());

    // }

    // @Test
    // public void testQueryForGetCaptainByNameNonExistantId() throws Exception {
    //     String name = "Ragnor";
    //     when(mockBusinessService.findCaptainByShipName(name))
    //             .thenReturn(null);

    //     mockMvc.perform(get("/ships/captain/" + name))
    //             .andDo(print())
    //             .andExpect(status().isNoContent());

    // }

    // @Test
    // public void testQueryForGetCaptainByShipNameDaoException() throws Exception {
    //     String name = "Ragnor";
    //     when(mockBusinessService.findCaptainByShipName(name))
    //             .thenThrow(new RuntimeException("mock exception"));

    //     mockMvc.perform(get("/ships/captain/" + name))
    //             .andDo(print())
    //             .andExpect(status().isInternalServerError());

    // }

    // @Test
    // public void testQueryForShipById() throws Exception {
    //     int id = 1;
    //     Ship ship = expectedShipList.get(0);


    //     when(mockBusinessService.findShipById(id))
    //             .thenReturn(ship);

    //     mockMvc.perform(get("/ships/" + id))
    //             .andDo(print())
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.name").value("RMS Titanic"));

    // }

    // @Test
    // public void testQueryForGetShipInvalidId() throws Exception {
    //     int id = -1;

    //     mockMvc.perform(get("/ships/" + id))
    //             .andDo(print())
    //             .andExpect(status().isBadRequest());

    // }

    // @Test
    // public void testQueryForGetShipsByIdNonExistantId() throws Exception {
    //     int id = 99;
    //     when(mockBusinessService.findShipById(id))
    //             .thenReturn(null);

    //     mockMvc.perform(get("/ships/" + id))
    //             .andDo(print())
    //             .andExpect(status().isNoContent());

    // }

    // @Test
    // public void testQueryForGetShipByIdDaoException() throws Exception {
    //     int id = 5;
    //     when(mockBusinessService.findShipById(id))
    //             .thenThrow(new RuntimeException("mock exception"));

    //     mockMvc.perform(get("/ships/" + id))
    //             .andDo(print())
    //             .andExpect(status().isInternalServerError());

    // }

}