package com.fidelity.business.service;

import com.fidelity.business.Ship;
import com.fidelity.integration.ShipDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ShipBusinessServicePojoUnitTest {

    @Mock
    private ShipDao shipDao;

    @InjectMocks
    private ShipBusinessServiceImpl shipBusinessService;

    Ship ship1 = new Ship(1, "RMS Titanic", null, "Captain Edward J. Smith", "Undisputedly the most famous ship in maritime history to encounter the most tragic event could be this luxury cruise from the British White Star liner with a connotation to showcase mankindï¿½s technological brilliance.  On its maiden voyage on April 10, 1912 from Southampton to New York, it had struck an iceberg five days later and sank in the North Atlantic, failing to evacuate about 1500 passengers onboard. Rediscovered 1985, this historic ship with its equally historic tale has become the inspiration of multitude of documentaries and also the backdrop to one of the most successful Hollywood flick in 1999.", "Luxury liner");

    Ship ship2 = new Ship(2, "Bismarck", null, "Otto Ernst Lindeman",
            "With a length of 823 feet and a top speed of 30 knots, this giant historic ship was undoubtedly the largest and fastest warship afloat in 1941 to have struck a terror at the heart of the British Navy. After inflicting enough damage to the British fleet of battle ships it was sunk at the bottom of the sea. However, after it was recovered in 1989, the founding indicated that this epitome of warship in the maritime history might have been scuttled rather than sunk by the British.", "German battleship");

    Ship ship3 = new Ship(3, "Starship Enterprise", null, "James T. Kirk",
            "Enterprise NCC-1701 was built in the San Francisco Yards orbiting Earth. The Constitution-class starship was previously captained by Robert April and Christopher Pike, before coming under the command of Captainï¿½Jamesï¿½T.ï¿½Kirk.", "Enterprise-D Galaxy class Federation starship");

    private List<Ship> shipList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllShips() {
        shipList = Arrays.asList(ship1, ship2, ship3);
        when(shipDao.queryAllShips()).thenReturn(shipList);
        assertEquals(shipList, shipBusinessService.findAllShips());
    }

    @Test
    void testFindAllShips_DaoReturnsEmptyList() {
        when(shipDao.queryAllShips()).thenReturn(new ArrayList<Ship>());
        assertEquals(new ArrayList<Ship>(), shipBusinessService.findAllShips());
    }

    @Test
    void testFindAllShips_DaoThrowsException() {
        when(shipDao.queryAllShips()).thenThrow(new RuntimeException("Mock DAO Exception"));
        assertThrows(ShipDatabaseException.class, () -> {
            shipBusinessService.findAllShips();
        });
    }

    @Test
    void testFindShipById() {
        int id = 1;
        when(shipDao.queryShipById(id)).thenReturn(ship1);
        Ship ship = shipBusinessService.findShipById(id);
        assertEquals(ship1, ship);
    }

    @Test
    void testFindShipById_ZeroId() {
        int id = 0;
        when(shipDao.queryShipById(id)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> {
            shipBusinessService.findShipById(id);
        });
    }

    @Test
    void testFindShipById_NegativeId() {
        int id = -1;
        when(shipDao.queryShipById(id)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> {
            shipBusinessService.findShipById(id);
        });
    }

    @Test
    void testFindShipByName() {
        String name = "RMS Titanic";
        when(shipDao.queryCaptainByShipName(name)).thenReturn(ship1.getCaptain());
        String captain = shipBusinessService.findCaptainByShipName(name);
        assertEquals(ship1.getCaptain(), captain);
    }

    @Test
    void testFindShipById_EmptyStringName() {
        String name = "";
        when(shipDao.queryCaptainByShipName(name)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> {
            shipBusinessService.findCaptainByShipName(name);
        });
    }

}