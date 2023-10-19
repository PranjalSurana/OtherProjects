package com.fidelity.integration;

import com.fidelity.business.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.jdbc.JdbcTestUtils.deleteFromTables;

/**
 * DaoTest is an integration test for ShipDaoMyBatisImpl.
 * 
 * To verify that the DAO is actually working, we'll need to query the database 
 * directly, so we'll use Spring's JdbcTestUtils class, which has methods like 
 * countRowsInTable() and deleteFromTables().
 *
 * @author ROI Instructor
 *
 */
@SpringBootTest
@Transactional
class ShipDaoTest {

    @Autowired
    private ShipDao shipDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    Ship ship1 = new Ship(1, "RMS Titanic", null, "Captain Edward J. Smith", "Desc1", "Luxury liner");

    Ship ship2 = new Ship(2, "Bismarck", null, "Otto Ernst Lindeman",
            "Desc2", "German battleship");

    Ship ship3 = new Ship(3, "HMS Victory", null, "C3",
            "Desc3", "French and Spanish wooden warship");

    @Test
    void testQueryAllShips_Success() {
        List<Ship> ships = shipDao.queryAllShips();
        assertTrue(!ships.isEmpty());
        assertEquals(ship1, ships.get(0));
        assertEquals(ship2, ships.get(1));
        assertEquals(ship3, ships.get(2));
    }

    @Test
    void testQueryAllShips_WidgetsTableIsEmpty() {
        deleteFromTables(jdbcTemplate, "FamousShips");
        List<Ship> widgets = shipDao.queryAllShips();
        assertEquals(new ArrayList<Ship>(), widgets);
    }

    @Test
    void testQueryShipById_Present() {
        Ship ship = shipDao.queryShipById(1);
        assertEquals(ship1, ship);
    }

    @Test
    void testQueryShipById_NotPresent() {
        Ship ship = shipDao.queryShipById(200);
        assertNull(ship);
    }

    @Test
    void testQueryShipById_ZeroId() {
        Ship ship = shipDao.queryShipById(0);
        assertNull(ship);
    }

    @Test
    void testQueryShipById_NegativeId() {
        Ship ship = shipDao.queryShipById(-1);
        assertNull(ship);
    }

    @Test
    void testQueryShipByName_Present() {
        String ship = shipDao.queryCaptainByShipName("RMS Titanic");
        assertEquals(ship1.getCaptain(), ship);
    }

    @Test
    void testQueryShipByName_NotPresent() {
        String ship = shipDao.queryCaptainByShipName("New Ship");
        assertNull(ship);
    }

    @Test
    void testQueryShipByName_EmptyNameString() {
        String ship = shipDao.queryCaptainByShipName("");
        assertNull(ship);
    }

}