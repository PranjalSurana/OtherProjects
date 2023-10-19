package com.fidelity.restcontroller;

import com.fidelity.business.Ship;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.*;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql({"classpath:schema.sql", "classpath:data.sql"})
public class ShipControllerE2eTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    Ship ship1 = new Ship(1, "RMS Titanic", null, "Captain Edward J. Smith", "Desc1", "Luxury liner");

    Ship ship2 = new Ship(2, "Bismarck", null, "Otto Ernst Lindeman",
            "Desc2", "German battleship");

    Ship ship3 = new Ship(3, "HMS Victory", null, "C3",
            "Desc3", "French and Spanish wooden warship");


    @Test
    public void testQueryForAllShips() {
        int shipCount = countRowsInTable(jdbcTemplate, "FamousShips");
        String requestUrl = "/ships";
        ResponseEntity<Ship[]> response = restTemplate.getForEntity(requestUrl, Ship[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Ship[] responseShips = response.getBody();
        assertEquals(shipCount, responseShips.length);
        assertEquals(ship1, responseShips[0]);
        assertEquals(ship3, responseShips[2]);
    }

    @Test
    public void testQueryForAllShips_NoShipsInDb() {
        deleteFromTables(jdbcTemplate, "FamousShips");
        String requestUrl = "/ships";
        ResponseEntity<Ship[]> response = restTemplate.getForEntity(requestUrl, Ship[].class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testQueryForShipById() {
        String requestUrl = "/ships/id/3";
        ResponseEntity<Ship> response = restTemplate.getForEntity(requestUrl, Ship.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Ship responseBody = response.getBody();
        assertEquals(ship3, responseBody);
    }

    @Test
    public void testQueryForShipById_NotPresent() {
        String requestUrl = "/ships/id/39";
        ResponseEntity<Ship[]> response = restTemplate.getForEntity(requestUrl, Ship[].class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testQueryForShipByName() {
        String requestUrl = "/ships/name/Bismarck";
        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String responseBody = response.getBody();
        assertEquals(ship2.getCaptain(), responseBody);
    }

    @Test
    public void testQueryForShipByName_NotPresent() {
        String requestUrl = "/ships/name/names";
        ResponseEntity<String> response = restTemplate.getForEntity(requestUrl, String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}