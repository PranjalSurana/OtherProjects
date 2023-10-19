package com.roifmr.presidents.restcontroller;

import com.roifmr.presidents.models.President;
import com.roifmr.presidents.restservices.PresidentBusinessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PresidentsControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PresidentBusinessService mockPresidentBusinessService;

    // Declare List of President
    // Declare Mock Data

    List<President> presidentList;

    President president1 = new President(1, "f1", "l1", 1789, 1795, "img1.jpg", "bio1");
    President president2 = new President(2, "f2", "l2", 1789, 1795, "img2.jpg", "bio2");
    President president3 = new President(3, "f3", "l3", 1789, 1795, "img3.jpg", "bio3");

    @Autowired
    ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        presidentList = new ArrayList<>();
        presidentList.add(president1);
        presidentList.add(president2);
        presidentList.add(president3);
    }

    @Test
    public void contextLoads() {
        assertNotNull(applicationContext);
    }

    @Test
    public void testPingEndpoint() throws Exception {
        mockMvc.perform(get("/presidents/ping").accept(MediaType.ALL_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testQueryForAllPresidents() throws Exception {
        when(mockPresidentBusinessService.findAllPresidents()).thenReturn(presidentList);
        mockMvc.perform(get("/presidents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].firstName").value("f1"))
                .andExpect(jsonPath("$[2].bio").value("bio3"));
    }

    @Test
    public void testQueryForAllPresidents_DaoReturnsEmptyList() throws Exception {
        when(mockPresidentBusinessService.findAllPresidents())
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/presidents"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(is(emptyOrNullString())));
    }
    
}