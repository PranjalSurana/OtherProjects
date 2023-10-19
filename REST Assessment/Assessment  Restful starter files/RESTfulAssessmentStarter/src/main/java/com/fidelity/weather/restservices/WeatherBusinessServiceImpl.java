package com.fidelity.weather.restservices;

import com.fidelity.weather.integration.WeatherDao;
import com.fidelity.weather.model.City;
import com.fidelity.weather.model.LatitudeAndLongitude;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WeatherBusinessServiceImpl implements WeatherBusinessService {

    @Autowired
    private WeatherDao weatherDao;

    @Autowired
    private Logger logger;

    @Override
    public LatitudeAndLongitude findLatitudeAndLongitude(String cityName) {
        LatitudeAndLongitude latitudeAndLongitude;
        try {
            latitudeAndLongitude = weatherDao.findLatitudeAndLongitude(cityName);
        }
        catch (Exception e) {
            logger.debug("Exception {} raised in findLatitudeAndLongitude()", e.getMessage());
            String msg = "Error querying all XYZs in the XYZ database.";
            throw new WeatherDatabaseException(msg, e);
        }
        return latitudeAndLongitude;
    }

    @Override
    public List<City> findWeatherForecast(int latitude, int longitude) {
        return null;
    }
}