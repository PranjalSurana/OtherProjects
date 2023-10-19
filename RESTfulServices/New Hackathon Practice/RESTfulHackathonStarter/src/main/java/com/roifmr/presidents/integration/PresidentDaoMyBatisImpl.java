package com.roifmr.presidents.integration;

import com.roifmr.presidents.integration.mapper.PresidentMapper;
import com.roifmr.presidents.models.President;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("presidentDao")
public class PresidentDaoMyBatisImpl implements PresidentDao {
    
    @Autowired
    private PresidentMapper presidentMapper;

    @Override
    public List<President> queryAllPresidents() {
        List<President> presidents = presidentMapper.getAllPresidents();

        return presidents;
    }

    @Override
    public President queryPresidentById(int id) {
        return presidentMapper.getPresidentById(id);
    }
    
}