package com.fidelity.india.secondary.assessment.integration.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MarinaMapper {

    @Select("""
            SELECT S.port_name
            FROM SEA_PORTS S
            JOIN VESSELS V
            ON S.ID = V.vessel_port
            WHERE V.vessel_name = #{vesselName}
            """)
    @Result(property="portName", column="port_name")
    String getMarinaName(String vesselName);


}