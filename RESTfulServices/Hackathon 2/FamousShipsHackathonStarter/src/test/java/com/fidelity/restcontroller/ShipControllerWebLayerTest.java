package com.fidelity.restcontroller;

import com.fidelity.business.Ship;
import com.fidelity.business.service.ShipBusinessService;
import com.fidelity.integration.mapper.ShipMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class ShipControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShipBusinessService mockShipBusinessService;

//    @MockBean
//    private ShipMapper shipMapper;

    private List<Ship> shipList;

    Ship ship1 = new Ship(1, "RMS Titanic", null, "Captain Edward J. Smith", "Undisputedly the most famous ship in maritime history to encounter the most tragic event could be this luxury cruise from the British White Star liner with a connotation to showcase mankindï¿½s technological brilliance.  On its maiden voyage on April 10, 1912 from Southampton to New York, it had struck an iceberg five days later and sank in the North Atlantic, failing to evacuate about 1500 passengers onboard. Rediscovered 1985, this historic ship with its equally historic tale has become the inspiration of multitude of documentaries and also the backdrop to one of the most successful Hollywood flick in 1999.", "Luxury liner");

    Ship ship2 = new Ship(2, "Bismarck", null, "Otto Ernst Lindeman",
            "With a length of 823 feet and a top speed of 30 knots, this giant historic ship was undoubtedly the largest and fastest warship afloat in 1941 to have struck a terror at the heart of the British Navy. After inflicting enough damage to the British fleet of battle ships it was sunk at the bottom of the sea. However, after it was recovered in 1989, the founding indicated that this epitome of warship in the maritime history might have been scuttled rather than sunk by the British.", "German battleship");

    Ship ship3 = new Ship(3, "Starship Enterprise", null, "James T. Kirk",
            "Enterprise NCC-1701 was built in the San Francisco Yards orbiting Earth. The Constitution-class starship was previously captained by Robert April and Christopher Pike, before coming under the command of Captainï¿½Jamesï¿½T.ï¿½Kirk.", "Enterprise-D Galaxy class Federation starship");

    @Autowired
    ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        shipList = new ArrayList<>();
        shipList.add(ship1);
        shipList.add(ship2);
        shipList.add(ship3);
    }

    @Test
    public void contextLoads() {
        assertNotNull(applicationContext);
    }

    @Test
    public void testPingEndpoint() throws Exception {
        mockMvc.perform(get("/ships/ping").accept(MediaType.ALL_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testQueryForAllShips() throws Exception {
        when(mockShipBusinessService.findAllShips()).thenReturn(shipList);
        mockMvc.perform(get("/ships"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("RMS Titanic"))
                .andExpect(jsonPath("$[2].captain").value("James T. Kirk"));
    }

    @Test
    public void testQueryForAllShips_DaoReturnsEmptyList() throws Exception {
        when(mockShipBusinessService.findAllShips())
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/ships"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(is(emptyOrNullString())));
    }

}