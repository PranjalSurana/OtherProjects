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

    private float latitude;
    private float longitude;
    private String temperatureUnit;
    private int highTemperature;
    private int lowTemperature;
    private String forecast;
    private String detailedForecast;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherForecast that)) return false;
        return Float.compare(getLatitude(), that.getLatitude()) == 0 && Float.compare(getLongitude(), that.getLongitude()) == 0 && getHighTemperature() == that.getHighTemperature() && getLowTemperature() == that.getLowTemperature() && Objects.equals(getTemperatureUnit(), that.getTemperatureUnit()) && Objects.equals(getForecast(), that.getForecast()) && Objects.equals(getDetailedForecast(), that.getDetailedForecast());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLatitude(), getLongitude(), getTemperatureUnit(), getHighTemperature(), getLowTemperature(), getForecast(), getDetailedForecast());
    }

    @Override
    public String toString() {
        return "WeatherForecast{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", temperatureUnit='" + temperatureUnit + '\'' +
                ", highTemperature=" + highTemperature +
                ", lowTemperature=" + lowTemperature +
                ", forecast='" + forecast + '\'' +
                ", detailedForecast='" + detailedForecast + '\'' +
                '}';
    }

}