package com.fidelity.restcontroller;

import com.fidelity.business.Ship;
import com.fidelity.business.service.ShipBusinessService;
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
@RequestMapping("/ships")
public class ShipController {

	@Autowired
	private ShipBusinessService shipBusinessService;

	@GetMapping(value="/ping", produces=MediaType.ALL_VALUE)
	public String ping() {
		return "ShipBusinessService is alive at " + LocalDateTime.now();
	}

	@GetMapping("")
	public ResponseEntity<List<Ship>> getAllShips() {
		List<Ship> ships;
		ResponseEntity<List<Ship>> response;
		try {
			ships = shipBusinessService.findAllShips();
		}
		catch(ServerErrorException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (!ships.isEmpty()) {
			response = ResponseEntity.status(HttpStatus.OK).body(ships);
		}
		else {
			response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return response;
	}
	
	// Get one ship by ID
	@GetMapping("id/{id}")
	public ResponseEntity<Ship> getShipById(@PathVariable("id") int id) {
		Ship ship = null;
		ResponseEntity<Ship> response;
		try {
			ship = shipBusinessService.findShipById(id);
		}
		catch(IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (ship != null) {
			response = ResponseEntity.status(HttpStatus.OK).body(ship);
		}
		else {
			response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return response;
	}

	@GetMapping("name/{name}")
	public ResponseEntity<String> getShipByName(@PathVariable("name") String name) {
		String captainName = null;
		ResponseEntity<String> response;
		try {
			captainName = shipBusinessService.findCaptainByShipName(name);
		}
		catch(IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (captainName != null) {
			response = ResponseEntity.status(HttpStatus.OK).body(captainName);
		}
		else {
			response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return response;
	}

}