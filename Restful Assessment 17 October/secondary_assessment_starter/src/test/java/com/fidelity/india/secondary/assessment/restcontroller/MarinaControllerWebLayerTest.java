package com.fidelity.india.secondary.assessment.restcontroller;

import com.fidelity.india.secondary.assessment.controller.MarinaController;
import com.fidelity.india.secondary.assessment.integration.MarinaDao;
import com.fidelity.india.secondary.assessment.model.Marina;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class MarinaControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MarinaDao mockDao;
    @MockBean
    private RestTemplate mockRestTemplate;

    @Test
    public void testGetForecast_Success() throws Exception {
        String vesselName = "Nitrogen";
        String portName = "Kanyakumari";

        when(mockDao.getMarinaName(vesselName)) .thenReturn(portName);

        Marina marina = new Marina("Kanyakumari", "India", "Nitrogen", 15416, 90, "₹1387440");

        ResponseEntity<Marina> response = ResponseEntity.ok(marina);
        String forecastUrl = MarinaController.MARINA_URL + portName + "/" + vesselName;
        when(mockRestTemplate.getForEntity(forecastUrl, Marina.class))
                .thenReturn(response);

        mockMvc.perform(get("/marina/Nitrogen"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marina").value("Kanyakumari"))
                .andExpect(jsonPath("$.country").value("India"))
                .andExpect(jsonPath("$.vesselName").value("Nitrogen"));
    }

    @Test
    public void testGetForecast_VesselNameInLowerCase_Success() throws Exception {
        String vesselName = "nitrogen";
        String portName = "Kanyakumari";

        when(mockDao.getMarinaName(vesselName)) .thenReturn(portName);

        Marina marina = new Marina("Kanyakumari", "India", "Nitrogen", 15416, 90, "₹1387440");

        ResponseEntity<Marina> response = ResponseEntity.ok(marina);
        String forecastUrl = MarinaController.MARINA_URL + portName + "/" + vesselName;
        when(mockRestTemplate.getForEntity(forecastUrl, Marina.class))
                .thenReturn(response);

        mockMvc.perform(get("/marina/nitrogen"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marina").value("Kanyakumari"))
                .andExpect(jsonPath("$.country").value("India"))
                .andExpect(jsonPath("$.vesselName").value("Nitrogen"));
    }

    @Test
    public void testGetForecast_VesselNameInUpperCase_Success() throws Exception {
        String vesselName = "NITROGEN";
        String portName = "Kanyakumari";

        when(mockDao.getMarinaName(vesselName)) .thenReturn(portName);

        Marina marina = new Marina("Kanyakumari", "India", "Nitrogen", 15416, 90, "₹1387440");

        ResponseEntity<Marina> response = ResponseEntity.ok(marina);
        String forecastUrl = MarinaController.MARINA_URL + portName + "/" + vesselName;
        when(mockRestTemplate.getForEntity(forecastUrl, Marina.class))
                .thenReturn(response);

        mockMvc.perform(get("/marina/NITROGEN"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marina").value("Kanyakumari"))
                .andExpect(jsonPath("$.country").value("India"))
                .andExpect(jsonPath("$.vesselName").value("Nitrogen"));
    }

    @Test
    public void testGetForecast_VesselNameInRandomCase_Success() throws Exception {
        String vesselName = "NiTrOGen";
        String portName = "Kanyakumari";

        when(mockDao.getMarinaName(vesselName)) .thenReturn(portName);

        Marina marina = new Marina("Kanyakumari", "India", "Nitrogen", 15416, 90, "₹1387440");

        ResponseEntity<Marina> response = ResponseEntity.ok(marina);
        String forecastUrl = MarinaController.MARINA_URL + portName + "/" + vesselName;
        when(mockRestTemplate.getForEntity(forecastUrl, Marina.class))
                .thenReturn(response);

        mockMvc.perform(get("/marina/NiTrOGen"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marina").value("Kanyakumari"))
                .andExpect(jsonPath("$.country").value("India"))
                .andExpect(jsonPath("$.vesselName").value("Nitrogen"));
    }

    @Test
    public void testGetForecast_EmptyVessel_NotFound() throws Exception {

        String vesselName = "";
        String portName = "Kanyakumari";

        when(mockDao.getMarinaName(vesselName)) .thenReturn(portName);

        ResponseEntity<Marina> response = ResponseEntity.ok(new Marina());
        String forecastUrl = MarinaController.MARINA_URL + portName + "/" + vesselName;
        when(mockRestTemplate.getForEntity(forecastUrl, Marina.class))
                .thenReturn(response);

        mockMvc.perform(get("/marina/Nitrogen"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}