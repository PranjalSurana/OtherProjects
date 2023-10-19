package com.fidelity.weather.integration.mapper;

import com.fidelity.weather.model.LatitudeAndLongitude;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WeatherMapper {
    LatitudeAndLongitude getLatitudeAndLongitude(@Param("city") String city, @Param("country") String countryCode, @Param("region") String regionCode);

}