package com.roifmr.resource.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

import com.roifmr.business.Gadget;
import com.roifmr.business.Product;
import com.roifmr.business.Widget;
import com.roifmr.integration.WarehouseDao;

@RestController
@RequestMapping("/warehouse")
@CrossOrigin(origins = "http://localhost:8089")  // enable CORS for Angular client
public class WarehouseController {
	private static final String DB_ERROR_MSG = 
			"Error communicating with the warehouse database";

	@Autowired
	private WarehouseDao dao;
	
	@GetMapping(value="/ping",
				produces=MediaType.ALL_VALUE)
	public String ping() {
		return "Wareshouse server is alive!";
	}
	
	// Widget methods
	
	@GetMapping(value="/widgets",
				produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Widget>> queryForAllWidgets() {
		ResponseEntity<List<Widget>> result;
		List<Widget> products;
		try {
			products = dao.getAllWidgets();
		} 
		catch (Exception e) {
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		
		if (products.size() > 0) {
			result = ResponseEntity.ok(products);
		}
		else {
			result = ResponseEntity.noContent().build();
		}
		return result;
	}

	@GetMapping(value="/widgets/{id}",
				produces=MediaType.APPLICATION_JSON_VALUE)
	public Widget queryForWidgetById(@PathVariable int id) {
		Widget widget = null;
		try {
			widget = dao.getWidget(id);
		} 
		catch (Exception e) {
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		if (widget == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"No widget in the warehouse with id = " + id);
		}
		return widget;
	}

	@DeleteMapping(value="/widgets/{id}",
				   produces=MediaType.APPLICATION_JSON_VALUE)
	public DatabaseRequestResult removeWidget(@PathVariable("id") int id) {
		int rows = 0;
		try {
			rows = dao.deleteWidget(id);
		} 
		catch (Exception e) {
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		if (rows == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"No widget in the warehouse with id = " + id);
		}
		return new DatabaseRequestResult(rows);
	}

	@PostMapping(value="/widgets",
				 produces=MediaType.APPLICATION_JSON_VALUE,
				 consumes=MediaType.APPLICATION_JSON_VALUE)
	public DatabaseRequestResult insertWidget(@RequestBody Widget widget) {
		int count = 0;
		try {
			count = dao.insertWidget(widget);
		} 
		catch (Exception e) {
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		if (count == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return new DatabaseRequestResult(count);
	}

	@PutMapping(value="/widgets",
					produces=MediaType.APPLICATION_JSON_VALUE,
					consumes=MediaType.APPLICATION_JSON_VALUE)
	public DatabaseRequestResult updateWidget(@RequestBody Widget widget) {
		int count = 0;
		try {
			count = dao.updateWidget(widget);
		} 
		catch (Exception e) {
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		return new DatabaseRequestResult(count);
	}

	// Gadget methods
	
	@GetMapping(value="/gadgets",
				produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Gadget>> queryForAllGadgets() {
		ResponseEntity<List<Gadget>> result;
		List<Gadget> products;
		try {
			products = dao.getAllGadgets();
		} 
		catch (Exception e) {
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		if (products.size() > 0) {
			result = ResponseEntity.ok(products);
		}
		else {
			result = ResponseEntity.noContent().build();
		}
		return result;
	}

	@GetMapping(value="/gadgets/{id}",
				produces=MediaType.APPLICATION_JSON_VALUE)
	public Gadget queryForGadgetById(@PathVariable("id") int id) {
		Gadget gadget = null;
		try {
			gadget = dao.getGadget(id);
		} 
		catch (Exception e) {
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		if (gadget == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"No gadgets in the warehouse with id = " + id);
		}
		return gadget;
	}

	@DeleteMapping(value="/gadgets/{id}",
				   produces=MediaType.APPLICATION_JSON_VALUE)
	public DatabaseRequestResult removeGadget(@PathVariable("id") int id) {
		int rows = 0;
		try {
			rows = dao.deleteGadget(id);
		} 
		catch (Exception e) {
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		if (rows == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"No gadgets in the warehouse with id = " + id);
		}
		return new DatabaseRequestResult(rows);
	}

	@PostMapping(value="/gadgets",
				 produces=MediaType.APPLICATION_JSON_VALUE,
				 consumes=MediaType.APPLICATION_JSON_VALUE)
	public DatabaseRequestResult insertGadget(@RequestBody Gadget gadget) {
		int count = 0;
		try {
			count = dao.insertGadget(gadget);
		} 
		catch (Exception e) {
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		if (count == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return new DatabaseRequestResult(count);
	}

	@PutMapping(value="/gadgets",
				produces=MediaType.APPLICATION_JSON_VALUE,
				consumes=MediaType.APPLICATION_JSON_VALUE)
	public DatabaseRequestResult updateGadget(@RequestBody Gadget gadget) {
		int count = 0;
		try {
			count = dao.updateGadget(gadget);
		} 
		catch (Exception e) {
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		if (count == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return new DatabaseRequestResult(count);
	}
	
	// Query for all products
	@GetMapping(value="/products",
				produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> queryForAllProducts() {
		ResponseEntity<List<Product>> result;
		List<Product> products = new ArrayList<>();
		try {
			List<Widget> widgets = dao.getAllWidgets();
			List<Gadget> gadgets = dao.getAllGadgets();

			products.addAll(widgets);
			products.addAll(gadgets);
		} 
		catch (Exception e) {
			throw new ServerErrorException(DB_ERROR_MSG, e);
		}
		if (products.size() > 0) {
			result = ResponseEntity.ok(products);
		}
		else {
			result = ResponseEntity.noContent().build();
		}
		return result;
	}
}
