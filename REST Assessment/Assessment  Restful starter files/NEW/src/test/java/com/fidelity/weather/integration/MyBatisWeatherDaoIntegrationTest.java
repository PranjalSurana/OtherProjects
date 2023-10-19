package com.fidelity.weather.integration;

import com.fidelity.weather.model.LatitudeAndLongitude;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MyBatisWeatherDaoIntegrationTest {

    @Autowired
    private WeatherDao dao;

    @Test
    public void getLatitudeAndLongitude_Success() {
        LatitudeAndLongitude expectedLatLong = new LatitudeAndLongitude(21.3099F, -157.8581F);

        LatitudeAndLongitude actualLatLong = dao.getLatitudeAndLongitude("Honolulu", "US", "HI");

        assertEquals(expectedLatLong, actualLatLong);
    }

    @Test
    public void getLatitudeAndLongitude_NotFound() {
        LatitudeAndLongitude actualLatLong = dao.getLatitudeAndLongitude("city", "country", "region");
        assertNull(actualLatLong);
    }

    @Test
    public void getLatitudeAndLongitude_EmptyCity_Exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            dao.getLatitudeAndLongitude("", "country", "region");
        });
    }

    @Test
    public void getLatitudeAndLongitude_NullCity_Exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            dao.getLatitudeAndLongitude(null, "country", "region");
        });
    }

    @Test
    public void getLatitudeAndLongitude_EmptyCountryCode_Exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            dao.getLatitudeAndLongitude("city", "", "region");
        });
    }

    @Test
    public void getLatitudeAndLongitude_NullCountryCode_Exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            dao.getLatitudeAndLongitude("city", null, "region");
        });
    }

    @Test
    public void getLatitudeAndLongitude_EmptyRegionCode_Exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            dao.getLatitudeAndLongitude("city", "country", "");
        });
    }

    @Test
    public void getLatitudeAndLongitude_NullRegionCode_Exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            dao.getLatitudeAndLongitude("city", "country", null);
        });
    }

}