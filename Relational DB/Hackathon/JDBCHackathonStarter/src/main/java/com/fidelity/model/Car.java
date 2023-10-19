package com.fidelity.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Car defines the properties of a vehicle.
 * 
 * @author ROI Instructor Team
 */
@Getter
@Setter
public class Car {
	// You may not change the datatype of any of the properties of Car
	private int id;
	private String make;
	private String model;
	private String bodyTypeName;
	private CarEngine engine;
	private LocalDate manufactureDate;

	public Car() {}

	public Car(int id, String make, String model, String bodyTypeName, CarEngine engine, 
			   LocalDate manufactureDate) {
		super();
		this.id = id;
		this.make = make;
		this.model = model;
		this.bodyTypeName = bodyTypeName;
		this.engine = engine;
		this.manufactureDate = manufactureDate;
	}

}
