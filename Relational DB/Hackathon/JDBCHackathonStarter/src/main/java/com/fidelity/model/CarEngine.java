package com.fidelity.model;

import lombok.Getter;
import lombok.Setter;

/**
 * CarEngine defines the properties of an engine for one Car's engine.
 * Car has a 1-to-1 relationship with CarEngine, but note that not
 * every car has an engine.
 * 
 * @author ROI Instructor Team
 */
@Getter
@Setter
public class CarEngine {
	// You may not change the datatype of any of the properties of CarEngine
	private int carId;
	private CarEngineType engineType;
	private float horsepower;
	private float engineSizeInLiters;

	public CarEngine() {}

	public CarEngine(int carId, CarEngineType engineType, float engineSizeInLiters,
			         float horsepower) {
		this.carId = carId;
		this.engineType = engineType;
		this.engineSizeInLiters = engineSizeInLiters;
		this.horsepower = horsepower;
	}

}
