package com.roifmr.presidents.restcontroller;

import com.roifmr.presidents.business.President;
import com.roifmr.presidents.business.service.PresidentDatabaseException;
import com.roifmr.presidents.business.service.PresidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PresidentController {

    @Autowired
    private PresidentService presidentService;

    @GetMapping(value="/ping",
            produces= MediaType.ALL_VALUE)
    public String ping() {
        return "Warehouse web presidentService is alive at " + LocalDateTime.now();
    }

    @GetMapping("/presidents")
    public ResponseEntity<List<President>> getAllPresidents() {
        List<President> presidents;
        ResponseEntity<List<President>> response;
        try {
            presidents = presidentService.findAllPresidents();
        }
        catch(ServerErrorException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        System.out.println(presidents);
        if (!presidents.isEmpty()) {
            response = ResponseEntity.status(HttpStatus.OK).body(presidents);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return response;
    }

    // Get one president by ID
    @GetMapping("/presidents/{id}")
    public ResponseEntity<President> getPresidentById(@PathVariable("id") int id) {
        President president = null;
        ResponseEntity<President> response;
        try {
            president = presidentService.findPresidentById(id);
        }
        catch(IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        catch (PresidentDatabaseException ex) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        response = ResponseEntity.status(HttpStatus.OK).body(president);
        return response;
    }

    // Insert a president
    @PostMapping("/presidents")
    public ResponseEntity<DatabaseRequestResult> addPresident(@RequestBody President president) {
        DatabaseRequestResult databaseRequestResult = new DatabaseRequestResult();
        int rowCount = 0;
        ResponseEntity<DatabaseRequestResult> response;
        if (president != null) {
            try {
                rowCount = presidentService.addPresident(president);
                databaseRequestResult.setRowCount(rowCount);
            }
            catch(IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body(databaseRequestResult);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return response;
    }

    // Update a president
    @PutMapping("/presidents")
    public ResponseEntity<DatabaseRequestResult> updatePresident(@RequestBody President president) {
        DatabaseRequestResult databaseRequestResult = new DatabaseRequestResult();
        int rowCount = 0;
        ResponseEntity<DatabaseRequestResult> response;
        if (president != null) {
            try {
                rowCount = presidentService.modifyPresident(president);
                databaseRequestResult.setRowCount(rowCount);
            }
            catch(IllegalArgumentException ex) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body(databaseRequestResult);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return response;
    }

    // Delete a president
    @DeleteMapping("/presidents/{id}")
    public ResponseEntity<DatabaseRequestResult> deletePresident(@PathVariable("id") int id) {
        DatabaseRequestResult databaseRequestResult = new DatabaseRequestResult();
        int rowCount = 0;
        ResponseEntity<DatabaseRequestResult> response;
        try {
            rowCount = presidentService.removePresident(id);
            databaseRequestResult.setRowCount(rowCount);
        }
        catch(IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (rowCount != 0) {
            response = ResponseEntity.status(HttpStatus.ACCEPTED).body(databaseRequestResult);
        }
        else {
            response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return response;
    }

}