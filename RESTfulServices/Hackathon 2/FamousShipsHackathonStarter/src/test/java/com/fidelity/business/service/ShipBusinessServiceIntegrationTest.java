package com.fidelity.business.service;

import com.fidelity.business.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.jdbc.JdbcTestUtils.deleteFromTables;

@SpringBootTest
@Transactional
public class ShipBusinessServiceIntegrationTest {

    @Autowired
    private ShipBusinessService shipBusinessService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<Ship> shipList;

    Ship ship1 = new Ship(1, "RMS Titanic", null, "Captain Edward J. Smith", "Desc1", "Luxury liner");

    Ship ship2 = new Ship(2, "Bismarck", null, "Otto Ernst Lindeman",
            "Desc2", "German battleship");

    Ship ship3 = new Ship(3, "HMS Victory", null, "C3",
            "Desc3", "French and Spanish wooden warship");

    @BeforeEach
    void setUp() {
        shipList = new ArrayList<>();
        shipList.add(ship1);
        shipList.add(ship2);
        shipList.add(ship3);
    }

    @Test
    void testGetAllWidgets() {
        List<Ship> ships = shipBusinessService.findAllShips();
        assertFalse(ships.isEmpty());
    }

    @Test
    void testGetAllWidgets_EmptyTable() {
        deleteFromTables(jdbcTemplate, "FamousShips");
        List<Ship> ships = shipBusinessService.findAllShips();
        assertTrue(ships.isEmpty());
    }

    @Test
    void testFindWidgetById() {
        Ship ship = shipBusinessService.findShipById(1);
        assertEquals(ship, ship1);
    }

    @Test
    void testFindWidgetById_ZeroId() {
        assertThrows(IllegalArgumentException.class, () -> {
            shipBusinessService.findShipById(0);
        });
    }

    @Test
    void testFindWidgetById_NegativeId() {
        assertThrows(IllegalArgumentException.class, () -> {
            shipBusinessService.findShipById(-1);
        });
    }

    @Test
    void testFindWidgetByName() {
        String captainName = shipBusinessService.findCaptainByShipName("RMS Titanic");
        assertEquals(captainName, ship1.getCaptain());
    }

    @Test
    void testFindWidgetByName_EmptyString() {
        assertThrows(IllegalArgumentException.class, () -> {
            shipBusinessService.findCaptainByShipName("");
        });
    }

    @Test
    void testFindWidgetByName_NullString() {
        assertThrows(IllegalArgumentException.class, () -> {
            shipBusinessService.findCaptainByShipName(null);
        });
    }


}