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
public class City {

    // CREATE TABLE CITY (
    //	ID NUMBER(10) PRIMARY KEY,
    //	CITY VARCHAR2(100) NOT NULL,
    //	COUNTRY_REGION_ID NUMBER(10) NOT NULL,
    //	LAT NUMBER(9, 6) NOT NULL,
    //	LON NUMBER(9, 6) NOT NULL,
    //	CONSTRAINT FK_CITY_COUNTRY_REGION FOREIGN KEY (COUNTRY_REGION_ID) REFERENCES COUNTRY_REGION(ID)
    //);

    private int cityId;
    private String city;
    private int countryRegionId;
    private int latitude;
    private int longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City city1)) return false;
        return getCityId() == city1.getCityId() && getCountryRegionId() == city1.getCountryRegionId() && getLatitude() == city1.getLatitude() && getLongitude() == city1.getLongitude() && Objects.equals(getCity(), city1.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCityId(), getCity(), getCountryRegionId(), getLatitude(), getLongitude());
    }

    @Override
    public String toString() {
        return "City{" +
                "cityId=" + cityId +
                ", city='" + city + '\'' +
                ", countryRegionId=" + countryRegionId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}