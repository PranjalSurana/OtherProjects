package com.roifmr.presidents.restcontroller;

import com.roifmr.presidents.models.President;
import com.roifmr.presidents.restservices.PresidentBusinessService;
import com.roifmr.presidents.restservices.PresidentDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/presidents")
public class PresidentController {

    @Autowired
    private PresidentBusinessService presidentBusinessService;

    @GetMapping(value="/ping", produces= MediaType.ALL_VALUE)
    public String ping() {
        return "PresidentBusinessService is alive at " + LocalDateTime.now();
    }

    @GetMapping("")
    public ResponseEntity<List<President>> getAllPresidents() {
        List<President> presidents;
        ResponseEntity<List<President>> response;
        try {
            presidents = presidentBusinessService.findAllPresidents();
        }
        catch(ServerErrorException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (!presidents.isEmpty()) {
            response = ResponseEntity.status(HttpStatus.OK).body(presidents);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return response;
    }

    // Get one president by ID
    @GetMapping("/{id}")
    public ResponseEntity<President> getPresidentById(@PathVariable("id") int id) {
        President president = null;
        ResponseEntity<President> response;
        try {
            president = presidentBusinessService.findPresidentById(id);
        }
        catch(IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        catch (PresidentDatabaseException ex) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        if (president != null) {
            response = ResponseEntity.status(HttpStatus.OK).body(president);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

}