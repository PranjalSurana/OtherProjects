package com.fidelity.weather.integration.mapper;

import com.fidelity.weather.model.City;
import com.fidelity.weather.model.LatitudeAndLongitude;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WeatherMapper {

    LatitudeAndLongitude getLatitudeAndLongitude(String cityName);

    List<City> getWeatherForecast(int latitude, int longitude);

}