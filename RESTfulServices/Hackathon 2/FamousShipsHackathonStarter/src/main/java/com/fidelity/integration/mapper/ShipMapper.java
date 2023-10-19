package com.fidelity.integration.mapper;

import java.util.List;

import com.fidelity.business.Ship;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ShipMapper {
	List<Ship> getAllShips();
	Ship getShipById(@Param("id") int id);
	String getCaptainByShipName(@Param("name") String name);

}
