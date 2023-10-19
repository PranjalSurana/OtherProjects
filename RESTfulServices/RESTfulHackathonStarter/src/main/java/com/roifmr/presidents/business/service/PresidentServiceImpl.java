package com.roifmr.presidents.business.service;

import com.roifmr.presidents.integration.PresidentDao;
import com.roifmr.presidents.business.President;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PresidentServiceImpl implements PresidentService {

    @Autowired
    private PresidentDao presidentDao;

    @Override
    public List<President> findAllPresidents() {
        List<President> Presidents;

        try {
            Presidents = presidentDao.getAllPresidents();
        }
        catch (Exception e) {
            String msg = "Error querying all Presidents in the Warehouse database.";
            throw new PresidentDatabaseException(msg, e);
        }

        return Presidents;
    }

    @Override
    public President findPresidentById(int id) {
        validateId(id);

        President president = null;
        try {
            president = presidentDao.getPresident(id);
        }
        catch (Exception e) {
            String msg = String.format("Error querying For President with id = %d in the President database.", id);
            throw new PresidentDatabaseException(msg, e);
        }
        if(president == null) {
            String msg = String.format("Error querying For President with id = %d in the President database.", id);
            throw new PresidentDatabaseException(msg);
        }
        return president;
    }

    @Override
    public int removePresident(int id) {
        validateId(id);

        int count = 0;
        try {
            count = presidentDao.deletePresident(id);
        }
        catch (Exception e) {
            String msg = String.format("Error removing President with id = %d the Warehouse database.", id);
            throw new PresidentDatabaseException(msg, e);
        }

        return count;
    }

    @Override
    public int addPresident(President President) {
        validatePresident(President);

        int count = 0;
        try {
            count = presidentDao.insertPresident(President);
        }
        catch(DuplicateKeyException e) {
            throw e;  // re-throw exception
        }
        catch (Exception e) {
            String msg = "Error inserting President into the Warehouse database.";
            throw new PresidentDatabaseException(msg, e);
        }

        return count;
    }

    @Override
    public int modifyPresident(President originalPresident) {
        validatePresident(originalPresident);

        int count = 0;
        try {
            count = presidentDao.updatePresident(originalPresident);
        }
        catch (Exception e) {
            String msg = "Error updating President in the Warehouse database.";
            throw new PresidentDatabaseException(msg, e);
        }

        return count;
    }

    private void validateId(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("invalid President id " + id);
        }
    }

    private void validatePresident(President president) {
        if (president == null) {
            throw new IllegalArgumentException("widget is not fully populated: " + president);
        }
        validateId(president.getId());
        if(president.getFirstName().isEmpty() || president.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First Name is not entered");
        }
        if(president.getLastName().isEmpty() || president.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last Name is not entered");
        }
        if(president.getStartYear() <= 0 || String.valueOf(president.getStartYear()).length() != 4) {
            throw new IllegalArgumentException("Incorrect Start Year is entered");
        }
        if(president.getEndYear() <= 0 || String.valueOf(president.getEndYear()).length() != 4) {
            throw new IllegalArgumentException("Incorrect End Year is entered");
        }
        if(president.getImagePath().isEmpty() || president.getImagePath().isBlank()) {
            throw new IllegalArgumentException("Image path is not entered");
        }
        if(president.getBio().isEmpty() || president.getBio().isBlank()) {
            throw new IllegalArgumentException("Bio is not entered");
        }
    }

}