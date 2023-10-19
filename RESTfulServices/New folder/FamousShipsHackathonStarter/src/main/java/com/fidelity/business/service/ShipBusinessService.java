package com.fidelity.business.service;

import com.fidelity.business.Ship;

import java.util.List;

public interface ShipBusinessService {

    List<Ship> findAllShips();
    Ship findShipById(int id);
    String findCaptainByShipName(String name);

}