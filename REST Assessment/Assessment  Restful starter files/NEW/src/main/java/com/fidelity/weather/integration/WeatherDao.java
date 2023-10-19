package com.fidelity.weather.integration;

import com.fidelity.weather.model.LatitudeAndLongitude;

public interface WeatherDao {
    LatitudeAndLongitude getLatitudeAndLongitude(String city, String countryCode, String regionCode);
}