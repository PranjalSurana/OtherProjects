package com.fidelity.restcontroller;

import java.time.LocalDateTime;
import java.util.List;

import com.fidelity.business.Widget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fidelity.business.service.WarehouseBusinessService;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

/**
 * WarehouseController is a RESTful web service.
 * It provides web methods to manage Widgets and Gadgets 
 * in the Warehouse database.
 * 
 * There is no business logic in this class; all business rules in
 * terms of validating data, defining transaction boundaries, etc.,
 * are implemented in the business service.
 * 
 * @author ROI Instructor
 *
 */

// TODO: add the required Spring annotations:
//       1. identify this class as a RESTful controller
//       2. configure the URL that will trigger method calls
// HINT: see slide 1-22
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
	
	// TODO: note the autowired business service field. The RESTful controller
	//       will delegate all input validation and database operations to the 
	//       business service.
	//       (no code changes required)
	@Autowired
	private WarehouseBusinessService service;

	@GetMapping(value="/ping",
				produces=MediaType.ALL_VALUE)
	public String ping() {
		return "Warehouse web service is alive at " + LocalDateTime.now();
	}
	
	// **** Widget methods ****
	
	// TODO: define CRUD operations for widgets
	
	// Get all widgets
	@GetMapping("/widgets")
	public ResponseEntity<List<Widget>> getAllWidgets() {
		List<Widget> widgets;
		ResponseEntity<List<Widget>> response;
		try {
			widgets = service.findAllWidgets();
		}
		catch(ServerErrorException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (!widgets.isEmpty()) {
			response = ResponseEntity.status(HttpStatus.OK).body(widgets);
		}
		else {
			response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return response;
	}
	
	// Get one widget by ID
	@GetMapping("/widgets/{id}")
	public ResponseEntity<Widget> getBookById(@PathVariable("id") int id) {
		Widget widget = null;
		ResponseEntity<Widget> response;
		try {
			widget = service.findWidgetById(id);
		}
		catch(IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (widget != null) {
			response = ResponseEntity.status(HttpStatus.OK).body(widget);
		}
		else {
			response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return response;
	}
	
	// Insert a widget
	@PostMapping("/widgets")
	public ResponseEntity<DatabaseRequestResult> addWidget(@RequestBody Widget widget) {
		DatabaseRequestResult databaseRequestResult = new DatabaseRequestResult();
		int rowCount = 0;
		ResponseEntity<DatabaseRequestResult> response;
		if (widget != null) {
			try {
				rowCount = service.addWidget(widget);
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
	
	// Update a widget
	@PutMapping("/widgets")
	public ResponseEntity<DatabaseRequestResult> updateBook(@RequestBody Widget widget) {
		DatabaseRequestResult databaseRequestResult = new DatabaseRequestResult();
		int rowCount = 0;
		ResponseEntity<DatabaseRequestResult> response;
		if (widget != null) {
			try {
				rowCount = service.modifyWidget(widget);
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

	// Delete a widget
	@DeleteMapping("/widget/{id}")
	public ResponseEntity<DatabaseRequestResult> deleteWidget(@PathVariable("id") int id) {
		DatabaseRequestResult databaseRequestResult = new DatabaseRequestResult();
		int rowCount;
		ResponseEntity<DatabaseRequestResult> response;
		try {
			rowCount = service.removeWidget(id);
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

	// **** Gadget methods ****

	// BONUS TODO: define CRUD operations for gadgets
	
	
}