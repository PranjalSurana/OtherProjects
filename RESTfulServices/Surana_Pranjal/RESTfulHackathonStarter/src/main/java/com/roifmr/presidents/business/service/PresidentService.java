package com.roifmr.presidents.business.service;

import com.roifmr.presidents.business.President;

import java.util.List;

public interface PresidentService {

    List<President> findAllPresidents();
    President findPresidentById(int id);
    int removePresident(int id);
    int addPresident(President widget);
    int modifyPresident(President originalPresident);

}