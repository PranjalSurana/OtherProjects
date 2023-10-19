package com.fidelity.weather.restcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fidelity.weather.integration.WeatherDao;
import com.fidelity.weather.model.LatitudeAndLongitude;
import com.fidelity.weather.model.WeatherForecast;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@WebMvcTest
public class WeatherControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WeatherDao mockDao;
    @MockBean
    private RestTemplate mockRestTemplate;

    @Test
    public void testGetForecast_Success() throws Exception {
        LatitudeAndLongitude latitudeAndLongitude = new LatitudeAndLongitude(21.3099F, -157.8581F);

        when(mockDao.getLatitudeAndLongitude("Honolulu", "US", "HI")) .thenReturn(latitudeAndLongitude);

        WeatherForecast forecast = new WeatherForecast(21.3099F, -157.8581F, "F", 83, 71, "Sunny", "Sunny, with a high near 83. East wind around 12 mph.");
        ResponseEntity<WeatherForecast> response = ResponseEntity.ok(forecast);
        String forecastUrl = WeatherController.FORECAST_URL + latitudeAndLongitude.getLatitude() + "," + latitudeAndLongitude.getLongitude();
        when(mockRestTemplate.getForEntity(forecastUrl, WeatherForecast.class))
                .thenReturn(response);

        mockMvc.perform(get("/weather/Honolulu/US/HI/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude").value("21.3099"))
                .andExpect(jsonPath("$.longitude").value("-157.8581"))
                .andExpect(jsonPath("$.temperatureUnit").value("F"))
                .andExpect(jsonPath("$.highTemperature").value("83"))
                .andExpect(jsonPath("$.lowTemperature").value("71"))
                .andExpect(jsonPath("$.forecast").value("Sunny"))
                .andExpect(jsonPath("$.detailedForecast").value("Sunny, with a high near 83. East wind around 12 mph."));
    }

    @Test
    public void testGetForecast_EmptyCity_NotFound() throws Exception {
        LatitudeAndLongitude latitudeAndLongitude = new LatitudeAndLongitude(21.3099F, -157.8581F);

        when(mockDao.getLatitudeAndLongitude("", "US", "HI")) .thenReturn(latitudeAndLongitude);

        WeatherForecast forecast = new WeatherForecast(21.3099F, -157.8581F, "F", 83, 71, "Sunny", "Sunny, with a high near 83. East wind around 12 mph.");
        ResponseEntity<WeatherForecast> response = ResponseEntity.ok(forecast);
        String forecastUrl = WeatherController.FORECAST_URL + latitudeAndLongitude.getLatitude() + "," + latitudeAndLongitude.getLongitude();
        when(mockRestTemplate.getForEntity(forecastUrl, WeatherForecast.class))
                .thenReturn(response);

        mockMvc.perform(get("/weather//US/HI/"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetForecast_EmptyCountryCode_NotFound() throws Exception {
        LatitudeAndLongitude latitudeAndLongitude = new LatitudeAndLongitude(21.3099F, -157.8581F);

        when(mockDao.getLatitudeAndLongitude("Honolulu", "", "HI")) .thenReturn(latitudeAndLongitude);

        WeatherForecast forecast = new WeatherForecast(21.3099F, -157.8581F, "F", 83, 71, "Sunny", "Sunny, with a high near 83. East wind around 12 mph.");
        ResponseEntity<WeatherForecast> response = ResponseEntity.ok(forecast);
        String forecastUrl = WeatherController.FORECAST_URL + latitudeAndLongitude.getLatitude() + "," + latitudeAndLongitude.getLongitude();
        when(mockRestTemplate.getForEntity(forecastUrl, WeatherForecast.class))
                .thenReturn(response);

        mockMvc.perform(get("/weather/Honolulu//HI/"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetForecast_EmptyRegionCode_NotFound() throws Exception {
        LatitudeAndLongitude latitudeAndLongitude = new LatitudeAndLongitude(21.3099F, -157.8581F);

        when(mockDao.getLatitudeAndLongitude("Honolulu", "US", "")) .thenReturn(latitudeAndLongitude);

        WeatherForecast forecast = new WeatherForecast(21.3099F, -157.8581F, "F", 83, 71, "Sunny", "Sunny, with a high near 83. East wind around 12 mph.");
        ResponseEntity<WeatherForecast> response = ResponseEntity.ok(forecast);
        String forecastUrl = WeatherController.FORECAST_URL + latitudeAndLongitude.getLatitude() + "," + latitudeAndLongitude.getLongitude();
        when(mockRestTemplate.getForEntity(forecastUrl, WeatherForecast.class))
                .thenReturn(response);

        mockMvc.perform(get("/weather/Honolulu/US//"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}