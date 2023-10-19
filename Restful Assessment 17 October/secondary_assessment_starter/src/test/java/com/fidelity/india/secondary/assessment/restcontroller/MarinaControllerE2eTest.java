package com.fidelity.india.secondary.assessment.restcontroller;

import com.fidelity.india.secondary.assessment.model.Marina;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * IMPORTANT: be sure to run the database initialization scripts in SQL Developer
 * before running these test cases:
 *	 src/main/resources/schema.sql
 *	 src/main/resources/data.sql
 */

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class MarinaControllerE2eTest {

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void testGetMarina_Success() {
        String request = "/marina/Bystolic";
        ResponseEntity<Marina> response = restTemplate.getForEntity(request, Marina.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetMarina_VesselNameInLowerCase_Success() {
        String request = "/marina/bystolic";
        ResponseEntity<Marina> response = restTemplate.getForEntity(request, Marina.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetMarina_VesselNameInUpperCase_Success() {
        String request = "/marina/BYSTOLIC";
        ResponseEntity<Marina> response = restTemplate.getForEntity(request, Marina.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetMarina_VesselNameInRandomCase_Success() {
        String request = "/marina/BysTolIc";
        ResponseEntity<Marina> response = restTemplate.getForEntity(request, Marina.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetMarina_UnknownVesselName () {
        String request = "/marina/vessel";
        ResponseEntity<Marina> response = restTemplate.getForEntity(request, Marina.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetMarina_InvalidUrl_VesselNameNotPassed () {
        String request = "/marina//";
        ResponseEntity<Marina> response = restTemplate.getForEntity(request, Marina.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}