package com.fidelity.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Warehouse {
	private String description;
	private List<Product> products;
	
	public Warehouse() {
		this("Standard Widgets and Gadgets Warehouse");
	}
	
	public Warehouse(String description) {
		this(description, new ArrayList<>());
	}
	
	public Warehouse(String description, List<Product> products) {
		this.description = description;
		this.products = products;
	}

	public String getDescription() {
		return description;
	}

	public List<Product> getProducts() {
		return products;
	}
	
	
}
