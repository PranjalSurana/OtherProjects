package com.fidelity.weather.restcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class WeatherApiEndpointTest {
	@InjectMocks
	private WeatherApiEndpoint endpoint;
	@Mock
	private Logger mockLogger;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '|', value = {
		"53.274,-9.0513|53.274|-9.0513|F|74|47|Clear|Mainly clear. High 74.", // Galway
		"21.3099,-157.8581|21.3099|-157.8581|F|83|71|Sunny|Sunny, with a high near 83. East wind around 12 mph.", // Honolulu
		"37.7749,-122.4194|37.7749|-122.4194|F|60|52|Foggy|Foggy, with a high near 60. West wind around 4 mph.", // San Francisco
		"32.7157,-117.1611|32.7157|-117.1611|F|75|53|Partly sunny|Partly sunny, with a high near 75. Southwest wind 5 to 10 mph.", // San Diego
		"47.6062,-122.3321|47.6062|-122.3321|F|65|49|Cloudy|Cloudy, with a high near 65. Northwest wind around 6 mph." // Seattle
	})
	void testGetForecastJson(String latLong, String lat, String lon, String units,
			String high, String low, String forecast, String detailedForecast) {

		ResponseEntity<WeatherForecast> forecastJsonResponse = endpoint.getForecast(latLong);
		
		WeatherForecast responseBody = forecastJsonResponse.getBody();
		assertEquals(Double.valueOf(lat), responseBody.getLatitude());
		assertEquals(Double.valueOf(lon), responseBody.getLongitude());
		assertEquals(units, responseBody.getTemperatureUnit());
		assertEquals(Integer.valueOf(high), responseBody.getHighTemperature());
		assertEquals(Integer.valueOf(low), responseBody.getLowTemperature());
		assertEquals(forecast, responseBody.getForecast());
		assertEquals(detailedForecast, responseBody.getDetailedForecast());
	}

	@Test
	void testGetForecastJson_NotFound() {
		ResponseEntity<WeatherForecast> forecastJsonResponse = endpoint.getForecast("0.0,0.0");
		
		assertEquals(HttpStatus.BAD_REQUEST, forecastJsonResponse.getStatusCode());
	}
}
