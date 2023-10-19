package com.fidelity.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fidelity.business.Widget;
import com.fidelity.integration.WarehouseDAOMyBatisImpl;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
	@Autowired
	WarehouseDAOMyBatisImpl dao;

	
	@GetMapping(value = "/widgets",
			produces = MediaType.APPLICATION_JSON_VALUE)  // JSON is the default, so we could omit this
	public List<Widget> queryAllWidgets() {
		List<Widget> widgets = new ArrayList<>();
		
		widgets = dao.getAllWidgets();
		
		return widgets;
	}
	
	@PostMapping(value = "/widgets", 
			consumes = MediaType.APPLICATION_JSON_VALUE,  // JSON is the default
			produces = MediaType.APPLICATION_JSON_VALUE)  // JSON is the default here too
	public Widget addWidget (@RequestBody Widget widget) {
		dao.insertWidget(widget);
		return widget;
	}

	// TODO: Create a web method to return all the Gadgets in the database
	
	
	// TODO: Create a web method to insert a new Gadget into the database
}
