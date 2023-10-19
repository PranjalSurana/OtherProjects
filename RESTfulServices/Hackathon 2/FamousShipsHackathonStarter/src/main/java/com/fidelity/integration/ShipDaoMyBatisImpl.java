package com.fidelity.integration;

import com.fidelity.business.Ship;
import com.fidelity.integration.mapper.ShipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ShipDao")
public class ShipDaoMyBatisImpl implements ShipDao {

    @Autowired
    private ShipMapper shipMapper;

    @Override
    public List<Ship> queryAllShips() {
        return shipMapper.getAllShips();
    }

    @Override
    public Ship queryShipById(int id) {
        return shipMapper.getShipById(id);
    }

    @Override
    public String queryCaptainByShipName(String name) {
        return shipMapper.getCaptainByShipName(name);
    }
}