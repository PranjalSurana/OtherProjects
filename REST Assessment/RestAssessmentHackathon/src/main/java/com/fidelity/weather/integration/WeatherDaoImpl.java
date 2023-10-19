package com.fidelity.weather.integration;

import com.fidelity.weather.integration.mapper.WeatherMapper;
import com.fidelity.weather.model.LatitudeAndLongitude;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("weatherDao")
public class WeatherDaoImpl implements WeatherDao {

    @Autowired
    private Logger logger;

    @Autowired
    private WeatherMapper weatherMapper;

    @Override
    public LatitudeAndLongitude getLatitudeAndLongitude(String cityName, String countryCode, String regionCode) {
        logger.debug("getLatitudeAndLongitude({}, {}, {})", cityName, countryCode, regionCode);
        if(cityName == null || cityName.isBlank()) {
            logger.debug("Null City name");
            throw new IllegalArgumentException("City should not be empty");
        }
        if(countryCode == null || countryCode.isBlank()) {
            logger.debug("Null Country Code");
            throw new IllegalArgumentException("Country Code should not be empty");
        }
        if(regionCode == null || regionCode.isBlank()) {
            logger.debug("Null Region Code");
            throw new IllegalArgumentException("Region Code should not be empty");
        }
        return weatherMapper.getLatitudeAndLongitude(cityName, countryCode, regionCode);
    }

}