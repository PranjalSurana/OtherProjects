package com.fidelity.weather.restcontroller;

import com.fidelity.weather.model.WeatherForecast;
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
public class WeatherControllerE2eTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetWeatherForecast_Success() {
        String request = "/weather/Honolulu/US/HI/";
        WeatherForecast weatherForecast = new WeatherForecast(21.3099F, -157.8581F, "F", 83, 71, "Sunny", "Sunny, with a high near 83. East wind around 12 mph.");
        ResponseEntity<WeatherForecast> response = restTemplate.getForEntity(request, WeatherForecast.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        WeatherForecast forecast = response.getBody();
        assertEquals(weatherForecast, forecast);
    }

    @Test
    public void testGetWeatherForecast_UnknownCity () {
        String request = "/weather/city/country/region";
        ResponseEntity<WeatherForecast> response = restTemplate.getForEntity(request, WeatherForecast.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGetWeatherForecast_InvalidUrl_CityNameNotPassed () {
        String request = "/weather//country/region";
        ResponseEntity<WeatherForecast> response = restTemplate.getForEntity(request, WeatherForecast.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetWeatherForecast_InvalidUrl_CountryCodeNotPassed () {
        String request = "/weather/city//region";
        ResponseEntity<WeatherForecast> response = restTemplate.getForEntity(request, WeatherForecast.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetWeatherForecast_InvalidUrl_RegionCodeNotPassed () {
        String request = "/weather/city/country/";
        ResponseEntity<WeatherForecast> response = restTemplate.getForEntity(request, WeatherForecast.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}