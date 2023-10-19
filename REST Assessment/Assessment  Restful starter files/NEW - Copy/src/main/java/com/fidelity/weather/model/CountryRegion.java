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
public class CountryRegion {

    // CREATE TABLE COUNTRY_REGION (
    //	ID NUMBER(10) PRIMARY KEY,
    //	COUNTRY_CODE VARCHAR2(2) NOT NULL,
    //	REGION_CODE VARCHAR2(2) NOT NULL
    //);

    private int countryRegionId;
    private int countryCode;
    private int regionCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryRegion that)) return false;
        return getCountryRegionId() == that.getCountryRegionId() && getCountryCode() == that.getCountryCode() && getRegionCode() == that.getRegionCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryRegionId(), getCountryCode(), getRegionCode());
    }

    @Override
    public String toString() {
        return "Country_Region{" +
                "countryRegionId=" + countryRegionId +
                ", countryCode=" + countryCode +
                ", regionCode=" + regionCode +
                '}';
    }

}