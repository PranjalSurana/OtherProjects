package com.fidelity.weather.integration;

import com.fidelity.weather.model.City;
import com.fidelity.weather.model.LatitudeAndLongitude;

import java.util.List;

public interface WeatherDao {

    LatitudeAndLongitude findLatitudeAndLongitude(String city);
    List<City> findWeatherForecast(int latitude, int longitude);

}