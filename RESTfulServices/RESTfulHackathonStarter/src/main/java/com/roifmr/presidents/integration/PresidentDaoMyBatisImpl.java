package com.roifmr.presidents.integration;

import com.roifmr.presidents.integration.mapper.PresidentMapper;
import com.roifmr.presidents.business.President;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("presidentDao")
public class PresidentDaoMyBatisImpl implements PresidentDao {

    @Autowired
    private PresidentMapper mapper;

    @Override
    public List<President> getAllPresidents() {
        List<President> presidents = mapper.getAllPresidents();
        return presidents;
    }

    @Override
    public President getPresident(int id) {
        President president = mapper.getPresident(id);
        return president;
    }

    @Override
    public int deletePresident(int id) {
        int count = mapper.deletePresident(id);
        return count;
    }

    @Override
    public int updatePresident(President president) {
        int count = mapper.updatePresident(president);
        return count;
    }

    @Override
    public int insertPresident(President president) {
        int count = mapper.insertPresident(president);
        return count;
    }
}