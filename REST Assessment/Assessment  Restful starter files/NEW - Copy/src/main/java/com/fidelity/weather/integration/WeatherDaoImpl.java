package com.fidelity.weather.integration;

import com.fidelity.weather.integration.mapper.WeatherMapper;
import com.fidelity.weather.model.LatitudeAndLongitude;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WeatherDaoImpl implements WeatherDao {
    @Autowired
    private Logger logger;

    @Autowired
    private WeatherMapper weatherMapper;

    @Override
    public LatitudeAndLongitude getLatitudeAndLongitude(String city, String countryCode, String regionCode) {
        logger.debug("getLatitudeAndLongitude({}, {}. {})", city, countryCode, regionCode);
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City should not be empty");
        }
        if (countryCode == null || countryCode.isBlank()) {
            throw new IllegalArgumentException("Country Code should not be empty");
        }
        if (regionCode == null || regionCode.isBlank()) {
            throw new IllegalArgumentException("Region Code should not be empty");
        }
        return weatherMapper.getLatitudeAndLongitude(city, countryCode, regionCode);
    }

}