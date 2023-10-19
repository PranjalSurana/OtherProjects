package com.fidelity.weather.restservices;

import com.fidelity.weather.model.City;
import com.fidelity.weather.model.LatitudeAndLongitude;

import java.util.List;

public interface WeatherBusinessService {

    LatitudeAndLongitude findLatitudeAndLongitude(String cityName);

    List<City> findWeatherForecast(int latitude, int longitude);
}