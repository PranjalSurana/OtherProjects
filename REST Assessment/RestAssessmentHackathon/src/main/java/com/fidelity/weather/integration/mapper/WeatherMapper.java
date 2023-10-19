package com.fidelity.weather.integration.mapper;

import com.fidelity.weather.model.LatitudeAndLongitude;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WeatherMapper {

    @Select("""
            SELECT C.LAT, C.LON
            FROM CITY C
            INNER JOIN COUNTRY_REGION CD
            ON C.ID = CD.ID
            WHERE C.CITY = #{cityName}
            AND CD.COUNTRY_CODE = #{countryCode}
            AND CD.REGION_CODE = #{regionCode}
            """)
    @Result(property="latitude", column="LAT")
    @Result(property="longitude", column="LON")
    LatitudeAndLongitude getLatitudeAndLongitude(String cityName, String countryCode, String regionCode);

}