package com.fidelity.weather.integration;

import com.fidelity.weather.model.LatitudeAndLongitude;

public interface WeatherDao {

    LatitudeAndLongitude getLatitudeAndLongitude(String cityName, String countryCode, String regionCode);
}