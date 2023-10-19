package com.roifmr.presidents.integration;

import com.roifmr.presidents.models.President;

import java.util.List;

public interface PresidentDao {

    List<President> queryAllPresidents();
    President queryPresidentById(int id);
    
}