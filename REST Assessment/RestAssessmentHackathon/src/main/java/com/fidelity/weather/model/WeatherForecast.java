package com.fidelity.weather.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherForecast {

    private String temperatureUnit;
    private int highTemperature;
    private int lowTemperature;
    private String forecast;
    private String detailedForecase;
    private LatitudeAndLongitude latitudeAndLongitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherForecast that)) return false;
        return getHighTemperature() == that.getHighTemperature() && getLowTemperature() == that.getLowTemperature() && Objects.equals(getTemperatureUnit(), that.getTemperatureUnit()) && Objects.equals(getForecast(), that.getForecast()) && Objects.equals(getDetailedForecase(), that.getDetailedForecase()) && Objects.equals(getLatitudeAndLongitude(), that.getLatitudeAndLongitude());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTemperatureUnit(), getHighTemperature(), getLowTemperature(), getForecast(), getDetailedForecase(), getLatitudeAndLongitude());
    }

    @Override
    public String toString() {
        return "WeatherForecast{" +
                "temperatureUnit='" + temperatureUnit + '\'' +
                ", highTemperature=" + highTemperature +
                ", lowTemperature=" + lowTemperature +
                ", forecast='" + forecast + '\'' +
                ", detailedForecase='" + detailedForecase + '\'' +
                ", latitudeAndLongitude=" + latitudeAndLongitude +
                '}';
    }

}