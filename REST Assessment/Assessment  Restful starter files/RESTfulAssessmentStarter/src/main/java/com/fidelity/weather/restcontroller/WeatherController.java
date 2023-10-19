package com.fidelity.weather.restcontroller;

import com.fidelity.weather.integration.WeatherDao;
import com.fidelity.weather.model.LatitudeAndLongitude;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    public static final String FORECAST_URL_BASE = "http://localhost:8088/forecast/";

    @Autowired
    private Logger logger;

    @Autowired
    private WeatherDao weatherDao;

    @GetMapping("/{city}")
    public ResponseEntity<LatitudeAndLongitude> getLatitudeAndLongitude(@PathVariable String city) {
        logger.debug("getLatitudeAndLongitude({}) executed", city);

        try {
            ResponseEntity<LatitudeAndLongitude> forecastResponse;
            LatitudeAndLongitude latitudeAndLongitude  = weatherDao.findLatitudeAndLongitude(city);

            if (latitudeAndLongitude == null) {
                LatitudeAndLongitude latitudeAndLongitude1 =
                        getForecastFromBackend(latLong.getLatitude(), latLong.getLongitude());

                // rubric: set HTTP status 200 if forecast is retrieved
                forecastResponse = ResponseEntity.ok(detailedForecast);
            }
            else {
                // rubric: log warnings and error messages
                logger.warn("getForecast: " + city + ", " + state + " not found");
                // rubric: set HTTP status 400 if there's no latitude/longitude for the given city
                forecastResponse = ResponseEntity.badRequest().build();
            }
            // rubric: return a ResponseEntity with the forecast
            logger.debug("getForecast({}, {}): return {}", city, state, forecastResponse);
            return forecastResponse;
        }
        // rubric: handle custom exception from weather API backend
        catch (WeatherException e) {
            logger.error("getForecast({}, {}): {}", city, state, e.getMessage());
            // rubric: set HTTP status 500
            throw new ServerErrorException(errorReason, e);
        }
        // rubric: handle other exceptions
        catch (RuntimeException e) {
            logger.error(errorReason, e);
            // rubric: set HTTP status 500
            throw new ServerErrorException(errorReason, e);
        }
    }

}