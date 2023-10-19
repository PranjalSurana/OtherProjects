package com.roifmr.presidents.restservices;

import com.roifmr.presidents.models.President;

import java.util.List;

public interface PresidentBusinessService {

    List<President> findAllPresidents();
    President findPresidentById(int id);

}