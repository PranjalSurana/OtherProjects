package com.fidelity.india.secondary.assessment.controller;

import com.fidelity.india.secondary.assessment.integration.MarinaDao;
import com.fidelity.india.secondary.assessment.model.Marina;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/marina")
public class MarinaController {

    public static final String MARINA_URL = "http://localhost:8888/marina/";

    @Autowired
    private Logger logger;

    @Autowired
    private MarinaDao marinaDao;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value="/ping", produces= MediaType.ALL_VALUE)
    public String ping() {
        return "Marina is alive at " + LocalDateTime.now();
    }


    @GetMapping("{vesselName}")
    public ResponseEntity<Marina> getPortName(@PathVariable("vesselName") String vesselName) {
        String errorReason = "Error getting Marina Name for " + vesselName;
        try {
            ResponseEntity<Marina> marinaResponseEntity;
            String portName = marinaDao.getMarinaName(vesselName);
            if (portName != null || !portName.isBlank()) {
                Marina marina = getBillFromBackend(portName, vesselName);
                marinaResponseEntity = ResponseEntity.ok(marina);
            }
            else {
                logger.warn("getPortName: " + vesselName + " not found");
                marinaResponseEntity = ResponseEntity.badRequest().build();
            }
            logger.debug("getPortName({})", vesselName);
            return marinaResponseEntity;
        }
        catch(NullPointerException | MarinaDatabaseException e) {
            logger.error("getPortName({}): {}", vesselName, e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        catch (RuntimeException e) {
            logger.error(errorReason, e);
            throw new ServerErrorException(errorReason, e);
        }
    }

    private Marina getBillFromBackend(String portName, String vesselName) {
        logger.debug("getBill({},{}): Enter", portName, vesselName);

        String marinaUrl = MARINA_URL + portName + "/" + vesselName;

        ResponseEntity<Marina> forecastResponse = restTemplate.getForEntity(marinaUrl, Marina.class);

        if (forecastResponse.getStatusCode() != HttpStatus.OK) {
            String message = "Error getting marina name for " + portName + "," + vesselName;
            throw new MarinaDatabaseException(message);
        }

        Marina marina = forecastResponse.getBody();

        logger.debug("getBill({},{}): return {}", portName, vesselName, marina);
        return marina;
    }

}