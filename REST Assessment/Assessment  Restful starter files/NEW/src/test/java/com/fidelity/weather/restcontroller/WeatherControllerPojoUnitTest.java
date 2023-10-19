package com.fidelity.weather.restweatherController;

import com.fidelity.weather.integration.WeatherDao;
import com.fidelity.weather.model.LatitudeAndLongitude;
import com.fidelity.weather.model.WeatherForecast;
import com.fidelity.weather.restcontroller.WeatherController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class WeatherControllerPojoUnitTest {

    @InjectMocks
    private WeatherController weatherController;
    @Mock
    private Logger logger;
    @Mock
    private RestTemplate mockRestTemplate;
    @Mock
    private WeatherDao weatherDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWeatherForecast_Success() {
        String city = "Honolulu";
        String countryCode = "US";
        String regionCode = "HI";
        float lat = 21.0F;
        float lon = -158.0F;
        when(weatherDao.getLatitudeAndLongitude(city, countryCode, regionCode)).thenReturn(new LatitudeAndLongitude(lat, lon));
        WeatherForecast weatherForecast = new WeatherForecast();
        weatherForecast.setDetailedForecast("Sunny, high 85");
        when(mockRestTemplate.getForEntity(WeatherController.FORECAST_URL + lat + "," + lon, WeatherForecast.class)) .thenReturn(ResponseEntity.ok(weatherForecast));
        ResponseEntity<WeatherForecast> response = weatherController.getWeatherForecast(city, countryCode, regionCode);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sunny, high 85", response.getBody().getDetailedForecast());
    }

    @Test
    public void testGetWeatherForecast_CityNotFound() {
        String city = "city";
        String countryCode = "US";
        String regionCode = "HI";
        when(weatherDao.getLatitudeAndLongitude(city, countryCode, regionCode)) .thenReturn(null);
        ResponseEntity<WeatherForecast> response = weatherController.getWeatherForecast(city, countryCode, regionCode);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testGetWeatherForecast_CountryNotFound() {
        String city = "Honolulu";
        String countryCode = "XX";
        String regionCode = "HI";
        when(weatherDao.getLatitudeAndLongitude(city, countryCode, regionCode)) .thenReturn(null);
        ResponseEntity<WeatherForecast> response = weatherController.getWeatherForecast(city, countryCode, regionCode);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testGetWeatherForecast_RegionNotFound() {
        String city = "Honolulu";
        String countryCode = "US";
        String regionCode = "XX";
        when(weatherDao.getLatitudeAndLongitude(city, countryCode, regionCode)) .thenReturn(null);
        ResponseEntity<WeatherForecast> response = weatherController.getWeatherForecast(city, countryCode, regionCode);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testGetWeatherForecast_EmptyCity_DaoException() {
        String city = "";
        String countryCode = "US";
        String regionCode = "HI";
        when(weatherDao.getLatitudeAndLongitude(city, countryCode, regionCode)) .thenThrow(new RuntimeException("Mock DAO exception"));
        assertThrows(ServerErrorException.class, () -> {
            weatherController.getWeatherForecast(city, countryCode, regionCode);
        });
    }

    @Test
    public void testGetWeatherForecast_EmptyCountryCode_DaoException() {
        String city = "Honolulu";
        String countryCode = "";
        String regionCode = "HI";
        when(weatherDao.getLatitudeAndLongitude(city, countryCode, regionCode)) .thenThrow(new RuntimeException("Mock DAO exception"));
        assertThrows(ServerErrorException.class, () -> {
            weatherController.getWeatherForecast(city, countryCode, regionCode);
        });
    }

    @Test
    public void testGetWeatherForecast_EmptyRegionCode_DaoException() {
        String city = "Honolulu";
        String countryCode = "US";
        String regionCode = "";
        when(weatherDao.getLatitudeAndLongitude(city, countryCode, regionCode)) .thenThrow(new RuntimeException("Mock DAO exception"));
        assertThrows(ServerErrorException.class, () -> {
            weatherController.getWeatherForecast(city, countryCode, regionCode);
        });
    }

    @Test
    public void testGetWeatherForecast_NullCity_DaoException() {
        String city = null;
        String countryCode = "US";
        String regionCode = "HI";
        when(weatherDao.getLatitudeAndLongitude(city, countryCode, regionCode)) .thenThrow(new RuntimeException("Mock DAO exception"));
        assertThrows(ServerErrorException.class, () -> {
            weatherController.getWeatherForecast(city, countryCode, regionCode);
        });
    }

    @Test
    public void testGetWeatherForecast_NullCountryCode_DaoException() {
        String city = "Honolulu";
        String countryCode = null;
        String regionCode = "HI";
        when(weatherDao.getLatitudeAndLongitude(city, countryCode, regionCode)) .thenThrow(new RuntimeException("Mock DAO exception"));
        assertThrows(ServerErrorException.class, () -> {
            weatherController.getWeatherForecast(city, countryCode, regionCode);
        });
    }

    @Test
    public void testGetWeatherForecast_NullRegionCode_DaoException() {
        String city = "Honolulu";
        String countryCode = "US";
        String regionCode = null;
        when(weatherDao.getLatitudeAndLongitude(city, countryCode, regionCode)) .thenThrow(new RuntimeException("Mock DAO exception"));
        assertThrows(ServerErrorException.class, () -> {
            weatherController.getWeatherForecast(city, countryCode, regionCode);
        });
    }

}