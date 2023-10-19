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
public class LatitudeAndLongitude {

    private String latitude;
    private String longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LatitudeAndLongitude that)) return false;
        return Objects.equals(getLatitude(), that.getLatitude()) && Objects.equals(getLongitude(), that.getLongitude());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLatitude(), getLongitude());
    }

    @Override
    public String toString() {
        return "LatitudeAndLongitude{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

}