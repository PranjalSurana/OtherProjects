package com.fidelity.integration;

import java.sql.SQLException;
import java.util.List;

import com.fidelity.model.Car;
import com.fidelity.model.CarEngine;

/**
 * CarDao defines the methods for all Car DAO implementations.
 * 
 * @author ROI Instructor Team
 */
public interface CarDao {

	// You may not make any changes to the methods
	List<Car> getCars();
	Car insertCarAndEngine(Car car, CarEngine carEngine) throws SQLException;
	Car updateCarAndCarEngine(Car car, CarEngine carEngine) throws SQLException;
	boolean deleteCar(int carId);
}
