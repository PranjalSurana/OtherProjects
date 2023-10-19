package com.roifmr.presidents.restservices;

import com.roifmr.presidents.models.President;
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
public class PresidentBusinessServiceIntegrationTest {

    @Autowired
    private PresidentBusinessService presidentBusinessService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    List<President> presidentList;

    President president1 = new President(1, "f1", "l1", 1789, 1795, "img1.jpg", "bio1");
    President president2 = new President(2, "f2", "l2", 1789, 1795, "img2.jpg", "bio2");
    President president3 = new President(3, "f3", "l3", 1789, 1795, "img3.jpg", "bio3");

    @BeforeEach
    void setUp() {
        presidentList = new ArrayList<>();
        presidentList.add(president1);
        presidentList.add(president2);
        presidentList.add(president3);
    }

    @Test
    void testGetAllPresidents() {
        List<President> presidents = presidentBusinessService.findAllPresidents();
        assertFalse(presidents.isEmpty());
    }

    @Test
    void testGetAllPresidents_EmptyTable() {
        deleteFromTables(jdbcTemplate, "Presidents");
        List<President> presidents = presidentBusinessService.findAllPresidents();
        assertTrue(presidents.isEmpty());
    }

    @Test
    void testFindPresidentById() {
        President president = presidentBusinessService.findPresidentById(1);
        assertEquals(president, president1);
    }

    @Test
    void testFindPresidentById_ZeroId() {
        assertThrows(IllegalArgumentException.class, () -> {
            presidentBusinessService.findPresidentById(0);
        });
    }

    @Test
    void testFindPresidentById_NegativeId() {
        assertThrows(IllegalArgumentException.class, () -> {
            presidentBusinessService.findPresidentById(-1);
        });
    }
    
}