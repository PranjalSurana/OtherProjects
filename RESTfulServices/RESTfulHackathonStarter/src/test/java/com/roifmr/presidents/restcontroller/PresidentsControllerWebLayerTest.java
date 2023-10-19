package com.roifmr.presidents.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roifmr.presidents.business.President;
import com.roifmr.presidents.business.service.PresidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ServerErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class PresidentsControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PresidentService presidentService;

    private List<President> presidents;

    @BeforeEach
    public void init() {
        presidents = Arrays.asList(
                new President(1,"f1","l1",1789,1797,"img1.jpg","bio1"),
                new President(2, "f2", "l2", 1989, 2000, "img2.jpg", "bio2"),
                new President(3, "f3", "l3", 1889, 1930, "img3.jpg", "bio3")
        );
    }

    @Test
    public void testQueryForPresidentById() throws Exception {
        int id = 1;
        President firstPresident = new President(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(presidentService.findPresidentById(id))
                .thenReturn(firstPresident);

        // In this test case, we'll be very thorough and test every property 
        // of the returned JSON

        mockMvc.perform(get("/presidents/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("f1"))
                .andExpect(jsonPath("$.lastName").value("l1"))
                .andExpect(jsonPath("$.startYear").value(1789))
                .andExpect(jsonPath("$.endYear").value(1797))
                .andExpect(jsonPath("$.imagePath").value("img1.jpg"))
                .andExpect(jsonPath("$.bio").value("bio1"));
    }
    
    @Test
    public void testQueryForAllPresidents() throws Exception {
        when(presidentService.findAllPresidents())
                .thenReturn(presidents);

        // The previous test case proves that the controller returns all the properties 
        // of a single president. So in this test case, we won't be as exhaustive in our 
        // testing: we'll just test one property of each returned JSON object.

        mockMvc.perform(get("/presidents"))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].firstName").value("f1"))
                .andExpect(jsonPath("$.[0].lastName").value("l1"))
                .andExpect(jsonPath("$.[0].startYear").value(1789))
                .andExpect(jsonPath("$.[0].endYear").value(1797))
                .andExpect(jsonPath("$.[0].imagePath").value("img1.jpg"))
                .andExpect(jsonPath("$.[0].bio").value("bio1"))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].firstName").value("f2"))
                .andExpect(jsonPath("$.[1].lastName").value("l2"))
                .andExpect(jsonPath("$.[1].startYear").value(1989))
                .andExpect(jsonPath("$.[1].endYear").value(2000))
                .andExpect(jsonPath("$.[1].imagePath").value("img2.jpg"))
                .andExpect(jsonPath("$.[1].bio").value("bio2"))
                .andExpect(jsonPath("$.[2].id").value(3))
                .andExpect(jsonPath("$.[2].firstName").value("f3"))
                .andExpect(jsonPath("$.[2].lastName").value("l3"))
                .andExpect(jsonPath("$.[2].startYear").value(1889))
                .andExpect(jsonPath("$.[2].endYear").value(1930))
                .andExpect(jsonPath("$.[2].imagePath").value("img3.jpg"))
                .andExpect(jsonPath("$.[2].bio").value("bio3"));
    }

    @Test
    public void testQueryForAllPresidents_DaoReturnsEmptyList() throws Exception {
        when(presidentService.findAllPresidents())
                .thenReturn(new ArrayList<President>());

        mockMvc.perform(get("/presidents"))
                //.andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().string(is(emptyOrNullString())));
    }

    @Test
    public void testUpdatePresidentInWarehouse() throws Exception {
        President president = new President(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(presidentService.modifyPresident(president))
                .thenReturn(1);

        // Creating the ObjectMapper object
        ObjectMapper mapper = new ObjectMapper();

        // Converting the Object to JSONString
        String jsonString = mapper.writeValueAsString(president);

        mockMvc.perform(put("/presidents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                //.andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.rowCount").value(1));
    }

    @Test
    public void testQueryForAllPresidents_DaoThrowsException() throws Exception {
        when(presidentService.findAllPresidents())
                .thenThrow(ServerErrorException.class);
        mockMvc.perform(get("/presidents"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testAddPresidentToWarehouse() throws Exception {
        President president = new President(1,"f1","l1",1789,1797,"img1.jpg","bio1");

        when(presidentService.addPresident(president))
                .thenReturn(1);

        // Creating the ObjectMapper object
        ObjectMapper mapper = new ObjectMapper();

        // Converting the Object to JSONString
        String jsonString = mapper.writeValueAsString(president);

        mockMvc.perform(post("/presidents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                //.andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.rowCount").value(1));
    }

    @Test
    public void testRemovePresidentFromWarehouse() throws Exception{

        when(presidentService.removePresident(10))
                .thenReturn(1);

        // Creating the ObjectMapper object
        ObjectMapper mapper = new ObjectMapper();

        // Converting the Object to JSONString
        String jsonString = mapper.writeValueAsString(10);

        mockMvc.perform(delete("/presidents/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                //.andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.rowCount").value(1));
    }
    
}