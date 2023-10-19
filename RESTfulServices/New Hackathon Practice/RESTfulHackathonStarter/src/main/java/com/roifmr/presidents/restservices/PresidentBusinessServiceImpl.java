package com.roifmr.presidents.restservices;

import com.roifmr.presidents.integration.PresidentDao;
import com.roifmr.presidents.models.President;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PresidentBusinessServiceImpl implements PresidentBusinessService {

    @Autowired
    private PresidentDao presidentDao;

    @Override
    public List<President> findAllPresidents() {
        List<President> presidents;

        try {
            presidents = presidentDao.queryAllPresidents();
        }
        catch (Exception e) {
            String msg = "Error querying all Presidents in the President database.";
            throw new PresidentDatabaseException(msg, e);
        }

        return presidents;
    }

    @Override
    public President findPresidentById(int id) {
        validateId(id);
        President president = null;
        try {
            president = presidentDao.queryPresidentById(id);
        }
        catch (Exception e) {
            String msg = String.format("Error querying For President with id = %d in the Warehouse database.", id);
            throw new PresidentDatabaseException(msg, e);
        }
        if(president == null) {
            String msg = String.format("Error querying For President with id = %d in the Warehouse database.", id);
            throw new PresidentDatabaseException(msg);
        }
        return president;
    }

    private void validateId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Invalid President Id " + id);
        }
    }

}