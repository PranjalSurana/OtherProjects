package com.roifmr.presidents.restcontroller;

import com.roifmr.presidents.models.President;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.deleteFromTables;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"},
     executionPhase = ExecutionPhase.AFTER_TEST_METHOD) 
public class PresidentsControllerE2eTest {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    List<President> presidentList;

    President president1 = new President(1, "f1", "l1", 1789, 1795, "img1.jpg", "bio1");
    President president2 = new President(2, "f2", "l2", 1789, 1795, "img2.jpg", "bio2");
    President president3 = new President(3, "f3", "l3", 1789, 1795, "img3.jpg", "bio3");

    @Test
    public void testQueryForAllPresidents() {
        int presidentCount = countRowsInTable(jdbcTemplate, "Presidents");
        String requestUrl = "/presidents";
        ResponseEntity<President[]> response = restTemplate.getForEntity(requestUrl, President[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        President[] responsePresidents = response.getBody();
        assertEquals(presidentCount, responsePresidents.length);
        assertEquals(president1, responsePresidents[0]);
        assertEquals(president3, responsePresidents[2]);
    }

    @Test
    public void testQueryForAllPresidents_NoPresidentsInDb() {
        deleteFromTables(jdbcTemplate, "Presidents");
        String requestUrl = "/presidents/1";
        ResponseEntity<President[]> response = restTemplate.getForEntity(requestUrl, President[].class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testQueryForPresidentById() {
        String requestUrl = "/presidents/3";
        ResponseEntity<President> response = restTemplate.getForEntity(requestUrl, President.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        President responseBody = response.getBody();
        assertEquals(president3, responseBody);
    }

    @Test
    public void testQueryForPresidentById_NotPresent() {
        String requestUrl = "/presidents/99";
        ResponseEntity<President[]> response = restTemplate.getForEntity(requestUrl, President[].class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}