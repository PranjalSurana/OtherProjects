package com.fidelity.india.secondary.assessment.restcontroller;

import com.fidelity.india.secondary.assessment.controller.MarinaController;
import com.fidelity.india.secondary.assessment.integration.MarinaDao;
import com.fidelity.india.secondary.assessment.model.Marina;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MarinaControllerPojoUnitTest {

    @InjectMocks
    private MarinaController marinaController;
    @Mock
    private Logger logger;
    @Mock
    private RestTemplate mockRestTemplate;
    @Mock
    private MarinaDao marinaDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void pingTest() {
        String response = marinaController.ping();
        assertNotNull(response);
    }

    @Test
    public void testHashCode() {
        Marina marina1 = new Marina("Marina1", "Country1", "Vessel1", 100.0, 7, "Balance1");
        Marina marina2 = new Marina("Marina1", "Country1", "Vessel1", 100.0, 7, "Balance1");

        assertEquals(marina1.hashCode(), marina2.hashCode());
    }

    @Test
    public void testEquals() {
        Marina marina1 = new Marina("Marina1", "Country1", "Vessel1", 100.0, 7, "Balance1");
        Marina marina2 = new Marina("Marina1", "Country1", "Vessel1", 100.0, 7, "Balance1");
        Marina marina3 = new Marina("Marina2", "Country2", "Vessel2", 200.0, 10, "Balance2");

        assertEquals(marina1, marina2);
        assertNotEquals(marina1, marina3);
    }

    @Test
    public void testToString() {
        Marina marina = new Marina("Marina1", "Country1", "Vessel1", 100.0, 7, "Balance1");
        String expectedString = "Marina{marina='Marina1', country='Country1', vesselName='Vessel1', dailyRate=100.0, billableNights=7, currentBalance='Balance1'}";

        assertEquals(expectedString, marina.toString());
    }

    @Test
    public void testGetMarinaName_Success() {
        String vesselName = "Nitrogen";
        when(marinaDao.getMarinaName(vesselName)).thenReturn("Kanyakumari");
        Marina marina = new Marina();
        marina.setCountry("India");
        when(mockRestTemplate.getForEntity(MarinaController.MARINA_URL + "Kanyakumari/" + vesselName, Marina.class)) .thenReturn(ResponseEntity.ok(marina));
        ResponseEntity<Marina> response = marinaController.getPortName(vesselName);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("India", response.getBody().getCountry());
    }

    @Test
    public void testGetMarinaName_VesselNameInLowerCase_Success() {
        String vesselName = "nitrogen";
        when(marinaDao.getMarinaName(vesselName)).thenReturn("Kanyakumari");
        Marina marina = new Marina();
        marina.setCountry("India");
        when(mockRestTemplate.getForEntity(MarinaController.MARINA_URL + "Kanyakumari/" + vesselName, Marina.class)) .thenReturn(ResponseEntity.ok(marina));
        ResponseEntity<Marina> response = marinaController.getPortName(vesselName);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("India", response.getBody().getCountry());
    }

    @Test
    public void testGetMarinaName_VesselNameInUpperCase_Success() {
        String vesselName = "NITROGEN";
        when(marinaDao.getMarinaName(vesselName)).thenReturn("Kanyakumari");
        Marina marina = new Marina();
        marina.setCountry("India");
        when(mockRestTemplate.getForEntity(MarinaController.MARINA_URL + "Kanyakumari/" + vesselName, Marina.class)) .thenReturn(ResponseEntity.ok(marina));
        ResponseEntity<Marina> response = marinaController.getPortName(vesselName);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("India", response.getBody().getCountry());
    }

    @Test
    public void testGetMarinaName_VesselNameInRandomCase_Success() {
        String vesselName = "NitrOGEn";
        when(marinaDao.getMarinaName(vesselName)).thenReturn("Kanyakumari");
        Marina marina = new Marina();
        marina.setCountry("India");
        when(mockRestTemplate.getForEntity(MarinaController.MARINA_URL + "Kanyakumari/" + vesselName, Marina.class)) .thenReturn(ResponseEntity.ok(marina));
        ResponseEntity<Marina> response = marinaController.getPortName(vesselName);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("India", response.getBody().getCountry());
    }

    @Test
    public void testGetMarinaName_VesselNotFound() {
        String vesselName = "vessel";
        when(marinaDao.getMarinaName(vesselName)) .thenReturn(null);
        assertThrows(ResponseStatusException.class, () -> {
            marinaController.getPortName(vesselName);
        });
    }

    @Test
    public void testGetMarinaName_EmptyVessel_DaoException() {
        String vesselName = "";
        when(marinaDao.getMarinaName(vesselName)) .thenThrow(new RuntimeException("Mock DAO exception"));
        assertThrows(ServerErrorException.class, () -> {
            marinaController.getPortName(vesselName);
        });
    }

    @Test
    public void testGetMarinaName_NullVessel_DaoException() {
        String vesselName = null;
        when(marinaDao.getMarinaName(vesselName)) .thenThrow(new RuntimeException("Mock DAO exception"));
        assertThrows(ServerErrorException.class, () -> {
            marinaController.getPortName(vesselName);
        });
    }

    @Test
    public void getPortName_ErrorInRestTemplate() {

        String vesselName = "Nitrogen";
        String portName = "MarinaName";

        when(marinaDao.getMarinaName(vesselName)).thenReturn(portName);
        when(mockRestTemplate.getForEntity(any(), any())).thenReturn(new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(ResponseStatusException.class, () -> marinaController.getPortName(vesselName));
    }

}