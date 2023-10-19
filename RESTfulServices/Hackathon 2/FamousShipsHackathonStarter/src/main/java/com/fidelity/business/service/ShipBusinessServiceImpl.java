package com.fidelity.business.service;

import com.fidelity.business.Ship;
import com.fidelity.integration.ShipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShipBusinessServiceImpl implements ShipBusinessService {

    @Autowired
    private ShipDao shipDao;

    @Override
    public List<Ship> findAllShips() {
        List<Ship> ships;

        try {
            ships = shipDao.queryAllShips();
        }
        catch (Exception e) {
            String msg = "Error querying all Ships in the Ship database.";
            throw new ShipDatabaseException(msg, e);
        }

        return ships;
    }

    @Override
    public Ship findShipById(int id) {
        validateId(id);
        Ship ship = null;
        try {
            ship = shipDao.queryShipById(id);
        }
        catch (Exception e) {
            String msg = String.format("Error querying For Ship with id = %d in the Warehouse database.", id);
            throw new ShipDatabaseException(msg, e);
        }
        if(ship == null) {
            String msg = String.format("Error querying For Ship with id = %d in the Warehouse database.", id);
            throw new ShipDatabaseException(msg);
        }
        return ship;
    }

    @Override
    public String findCaptainByShipName(String name) {
        validateName(name);
        String captainName = null;
        try {
            captainName = shipDao.queryCaptainByShipName(name);
        }
        catch (Exception e) {
            String msg = String.format("Error querying For Ship with name = %s in the Ship database.", name);
            throw new ShipDatabaseException(msg, e);
        }
        return captainName;
    }

    private void validateId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Invalid Ship Id " + id);
        }
    }

    private void validateName(String name) {
        if (name == "" || name == null) {
            throw new IllegalArgumentException("Invalid Ship Name " + name);
        }
    }
    
}