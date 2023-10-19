package com.fidelity.india.secondary.assessment.integration;

import com.fidelity.india.secondary.assessment.integration.mapper.MarinaMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MarinaDaoImpl implements MarinaDao {

    @Autowired
    private Logger logger;

    @Autowired
    private MarinaMapper mapper;

    @Override
    public String getMarinaName(String vesselName) {
        logger.debug("getMarinaName({})", vesselName);
        if(vesselName == null || vesselName.isBlank()) {
            logger.debug("Null Vessel name");
            throw new IllegalArgumentException("Vessel Name should not be empty");
        }
        return mapper.getMarinaName(convertToTitleCase(vesselName));
    }

    private String convertToTitleCase(String vesselName) {
        return vesselName.toUpperCase().charAt(0) + vesselName.toLowerCase().substring(1);
    }

}