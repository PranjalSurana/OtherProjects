package com.fidelity.weather.integration;

import com.fidelity.weather.integration.mapper.WeatherMapper;
import com.fidelity.weather.model.City;
import com.fidelity.weather.model.LatitudeAndLongitude;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WeatherDaoMyBatisImpl implements WeatherDao {

    @Autowired
    private Logger logger;

    @Autowired
    private WeatherMapper weatherMapper;

    @Override
    public LatitudeAndLongitude findLatitudeAndLongitude(String city) {
        logger.debug("findLatitudeAndLongitude({})", city);
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City should not be empty");
        }
        return weatherMapper.getLatitudeAndLongitude(city);
    }

    @Override
    public List<City> findWeatherForecast(int latitude, int longitude) {
        return null;
    }
}