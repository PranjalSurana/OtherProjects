package com.fidelity.weather.integration.mapper;

import com.fidelity.weather.model.LatitudeAndLongitude;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeatherMapper {
    LatitudeAndLongitude getLatitudeAndLongitude(String city, String countryCode, String regionCode);

}