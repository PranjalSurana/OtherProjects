package com.roifmr.presidents.business.service;

import com.roifmr.presidents.business.President;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PresidentBusinessServiceIntegrationTest {

    @Autowired
    private PresidentService presidentService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    List<President> presidents;

    private President president1 = new President(1,"George","Washington",1789,1797,"georgewashington.jpg",
            "On April 30, 1789, George Washington, standing on the balcony of Federal Hall on Wall Street "
                    + "in New York, took his oath of office as the first President of the United States. "
                    + "\"As the first of every thing, in our situation will serve to establish a Precedent,\" "
                    + "he wrote James Madison, \"it is devoutly wished on my part, that these precedents may be "
                    + "fixed on true principles.\" Born in 1732 into a Virginia planter family, he learned the "
                    + "morals, manners, and body of knowledge requisite for an 18th century Virginia gentleman.");

    private President president2 = new President(10,"John","Tyler",1841,1845,"johntyler.jpg",
            "Dubbed \"His Accidency\" by his detractors, John Tyler was the first Vice President to be "
                    + "elevated to the office of President by the death of his predecessor. Born in Virginia in "
                    + "1790, he was raised believing that the Constitution must be strictly construed. He never "
                    + "wavered from this conviction. He attended the College of William and Mary and studied law.");

    President president4 = new President(4, "f4", "l4", 1889, 1930, "img4.jpg", "bio4");

    @BeforeEach
    public void init() {
        presidents = new ArrayList<>();
        presidents.add(president1);
        presidents.add(president2);
    }

    @Test
    void testGetAllPresidents() {
        List<President> presidentList = presidentService.findAllPresidents();
        assertFalse(presidentList.isEmpty());
    }

    @Test
    void testFindPresidentById() {
        President president = presidentService.findPresidentById(1);
        assertEquals(president, president1);
    }

    @Test
    void testDeletePresident() {
        int deletedPresidentId = presidentService.removePresident(2);
        assertEquals(1, deletedPresidentId);
    }

    @Test
    void testInsertPresident() {
        int rowCount = presidentService.addPresident(president4);
        assertEquals(1, rowCount);
    }

    @Test
    void testUpdatePresident() {
        int rowCount = presidentService.modifyPresident(president1);
        assertEquals(1, rowCount);
        assertEquals("bio4", president4.getBio());
    }

    // ***** Utility Method to Load a President from the Database *****

    private President loadPresidentFromDb(int id) {
        String sql = "select * from presidents where id = " + id;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new President(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("startYear"),
                        rs.getInt("endYear"),
                        rs.getString("imagePath"),
                        rs.getString("lastName")));
    }

}