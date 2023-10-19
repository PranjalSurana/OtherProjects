package com.roifmr.presidents.integration;

import com.roifmr.presidents.models.President;
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
public class PresidentsMyBatisDaoIntegrationTest {

    @Autowired
    private PresidentDao presidentDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Create objects of type President

    List<President> presidentList;

    President president1 = new President(1, "f1", "l1", 1789, 1795, "img1.jpg", "bio1");
    President president2 = new President(2, "f2", "l2", 1789, 1795, "img2.jpg", "bio2");
    President president3 = new President(3, "f3", "l3", 1789, 1795, "img3.jpg", "bio3");

    @Test
    void testQueryAllPresidents_Success() {
        List<President> presidents = presidentDao.queryAllPresidents();
        assertTrue(!presidents.isEmpty());
        assertEquals(president1, presidents.get(0));
        assertEquals(president2, presidents.get(1));
        assertEquals(president3, presidents.get(2));
    }

    @Test
    void testQueryAllPresidents_PresidentsTableIsEmpty() {
        deleteFromTables(jdbcTemplate, "presidents");
        List<President> presidents = presidentDao.queryAllPresidents();
        assertEquals(new ArrayList<President>(), presidents);
    }

    @Test
    void testQueryPresidentById_Present() {
        President president = presidentDao.queryPresidentById(1);
        assertEquals(president1, president);
    }

    @Test
    void testQueryPresidentById_NotPresent() {
        President president = presidentDao.queryPresidentById(20000);
        assertNull(president);
    }

    @Test
    void testQueryPresidentById_ZeroId() {
        President president = presidentDao.queryPresidentById(0);
        assertNull(president);
    }

    @Test
    void testQueryPresidentById_NegativeId() {
        President president = presidentDao.queryPresidentById(-1);
        assertNull(president);
    }
    
}