package com.roifmr.presidents.restcontroller;

import com.roifmr.presidents.business.President;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.deleteFromTables;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class PresidentsControllerE2eTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    President president1 = new President(1, "George", "Washington", 1789, 1797, "georgewashington.jpg", "On April 30, 1789, George Washington, standing on the balcony of Federal Hall on Wall Street in New York, took his oath of office as the first President of the United States. \"As the first of every thing, in our situation will serve to establish a Precedent,\" he wrote James Madison, \"it is devoutly wished on my part, that these precedents may be fixed on true principles.\" Born in 1732 into a Virginia planter family, he learned the morals, manners, and body of knowledge requisite for an 18th century Virginia gentleman.");

    President president2 = new President(2, "John", "Adams", 1797, 1801, "johnadams.jpg", "Learned and thoughtful, John Adams was more remarkable as a political philosopher than as a politician. \"People and nations are forged in the fires of adversity,\" he said, doubtless thinking of his own as well as the American experience. Adams was born in the Massachusetts Bay Colony in 1735. A Harvard-educated lawyer, he early became identified with the patriot cause; a delegate to the First and Second Continental Congresses, he led in the movement for independence.");

    President president3 = new President(3, "Thomas", "Jefferson", 1801, 1809, "thomasjefferson.jpg", "In the thick of party conflict in 1800, Thomas Jefferson wrote in a private letter, \"I have sworn upon the altar of God eternal hostility against every form of tyranny over the mind of man.\" This powerful advocate of liberty was born in 1743 in Albemarle County, Virginia, inheriting from his father, a planter and surveyor, some 5,000 acres of land, and from his mother, a Randolph, high social standing. He studied at the College of William and Mary, then read law. In 1772 he married Martha Wayles Skelton, a widow, and took her to live in his partly constructed mountaintop home, Monticello.");

    @Test
    public void testQueryForAllPresidents() {
        int presidentCount = countRowsInTable(jdbcTemplate, "presidents");

        String request = "/presidents";

        ResponseEntity<President[]> response =
                restTemplate.getForEntity(request, President[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        President[] responsePresidents = response.getBody();
        assertEquals(presidentCount, responsePresidents.length);

        assertEquals(president1, responsePresidents[0]);
        assertEquals(president2, responsePresidents[1]);
    }


    @Test
    public void testQueryForAllPresidents_NoPresidentsInDb() {
        deleteFromTables(jdbcTemplate, "presidents");

        String request = "/presidents";

        ResponseEntity<String> response = restTemplate.getForEntity(request, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        assertTrue(response.getBody() == null || response.getBody().isBlank());
    }


    @Test
    public void testQueryForPresidentById() {
        String request = "/presidents/1";

        ResponseEntity<President> response =
                restTemplate.getForEntity(request, President.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(president1.getBio(), response.getBody().getBio());
    }


    @Test
    public void testQueryForPresidentById_NotPresent() {
        String request = "/presidents/99";

        ResponseEntity<President> response =
                restTemplate.getForEntity(request, President.class);



        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }
}