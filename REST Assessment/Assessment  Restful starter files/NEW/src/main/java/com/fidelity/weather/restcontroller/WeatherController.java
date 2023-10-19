package com.fidelity.weather.restcontroller;

import com.fidelity.weather.integration.WeatherDao;
import com.fidelity.weather.model.LatitudeAndLongitude;
import com.fidelity.weather.model.WeatherForecast;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerErrorException;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    public static final String FORECAST_URL = "http://localhost:8088/forecast/";

    @Autowired
    private Logger logger;

    @Autowired
    private WeatherDao weatherDao;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{city}/{countryCode}/{regionCode}")
    public ResponseEntity<WeatherForecast> getWeatherForecast(@PathVariable("city") String city, @PathVariable("countryCode") String countryCode, @PathVariable("regionCode") String regionCode) {
        
        logger.debug("getWeatherForecast({}, {}, {})", city, countryCode, regionCode);
        
        String errorReason = "Error getting Weather Forecast for " + city + ", " + countryCode + ", " + regionCode;

        try {  
            
            ResponseEntity<WeatherForecast> weatherForecastResponse;
            LatitudeAndLongitude latitudeAndLongitude = weatherDao.getLatitudeAndLongitude(city, countryCode, regionCode);

            if (latitudeAndLongitude != null) {
                WeatherForecast weatherForecast = getWeatherForecastFromAPI(latitudeAndLongitude.getLatitude(), latitudeAndLongitude.getLongitude());
                weatherForecastResponse = ResponseEntity.ok(weatherForecast);
            }
            else {
                logger.warn("getWeatherForecast(): " + city + ", " + countryCode, regionCode + " Not Found");
                weatherForecastResponse = ResponseEntity.badRequest().build();
            }
            logger.debug("getWeatherForecast({}, {}, {}): return {}", city, countryCode, regionCode, weatherForecastResponse);
            return weatherForecastResponse;
        }
        catch (WeatherDatabaseException e) {
            logger.error("getWeatherForecast({}, {}, {}): {}", city, countryCode, regionCode, e.getMessage());
            throw new ServerErrorException(errorReason, e);
        }
        catch (RuntimeException e) {
            logger.error(errorReason, e);
            throw new ServerErrorException(errorReason, e);
        }
    }

    private WeatherForecast getWeatherForecastFromAPI(float latitude, float longitude) {
        logger.debug("getWeatherForecast({},{}): enter", latitude, longitude);
        String forecastUrl = FORECAST_URL + latitude + "," + longitude;
        ResponseEntity<WeatherForecast> weatherForecastResponse = restTemplate.getForEntity(forecastUrl, WeatherForecast.class);

        if (weatherForecastResponse.getStatusCode() != HttpStatus.OK) {
            String message = "Error getting weather forecast for " + latitude + "," + longitude;
            throw new WeatherDatabaseException(message);
        }
        WeatherForecast weatherForecast = weatherForecastResponse.getBody();
        logger.debug("getWeatherForecast({},{}): return {}", latitude, longitude, weatherForecast);
        return weatherForecast;
    }
}
