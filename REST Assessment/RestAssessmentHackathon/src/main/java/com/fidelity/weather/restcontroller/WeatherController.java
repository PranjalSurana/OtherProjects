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

    @GetMapping("/{cityName}/{countryCode}/{regionCode}")
    public ResponseEntity<WeatherForecast> getWeatherForecast(@PathVariable("cityName") String cityName, @PathVariable("countryCode") String countryCode, @PathVariable("regionCode") String regionCode) {
        String errorReason = "Error getting Weather Forecast for " + cityName + ", " + countryCode + ", " + regionCode;
        try {
            ResponseEntity<WeatherForecast> weatherForecastResponseEntity;
            LatitudeAndLongitude latitudeAndLongitude = weatherDao.getLatitudeAndLongitude(cityName, countryCode, regionCode);
            if (latitudeAndLongitude != null) {
                WeatherForecast detailedForecast =
                        getForecastFromBackend(latitudeAndLongitude.getLatitude(), latitudeAndLongitude.getLongitude());
                weatherForecastResponseEntity = ResponseEntity.ok(detailedForecast);
            }
            else {
                logger.warn("getForecast: " + cityName + ", " + countryCode, regionCode + " not found");
                weatherForecastResponseEntity = ResponseEntity.badRequest().build();
            }
            logger.debug("getForecast({}, {}, {}): return {}", cityName, countryCode, regionCode, weatherForecastResponseEntity);
            return weatherForecastResponseEntity;
        }
        catch (WeatherDatabaseException e) {
            logger.error("getForecast({}, {}): {}", cityName, countryCode, regionCode, e.getMessage());
            throw new ServerErrorException(errorReason, e);
        }
        catch (RuntimeException e) {
            logger.error(errorReason, e);
            throw new ServerErrorException(errorReason, e);
        }
    }

    private WeatherForecast getForecastFromBackend(String latitude, String longitude) {
        logger.debug("getWeatherForecast({},{}): enter", latitude, longitude);

        String forecastUrl = FORECAST_URL + latitude + "," + longitude;

        ResponseEntity<WeatherForecast> forecastResponse =
                restTemplate.getForEntity(forecastUrl, WeatherForecast.class);

        if (forecastResponse.getStatusCode() != HttpStatus.OK) {
            String message = "Error getting forecast for " + latitude + "," + longitude;
            throw new WeatherDatabaseException(message);
        }

        WeatherForecast forecast = forecastResponse.getBody();

        logger.debug("getForecast({},{}): return {}", latitude, longitude, forecast);
        return forecast;
    }

}