package com.roifmr.abstractclassexample;

import java.util.List;

public class Driver {

	public static void main(String[] args) {
		Driver driver = new Driver();
		// Goal: get the total area of a list of different shapes
		List<Shape> allShapes = List.of(
			new Rectangle(0.0, 0.0, 1.0, 2.0), 
			new Triangle(2.0, 2.0, 3.0, 4.0),
			new Triangle(4.0, 4.0, 5.0, 6.0),
			new Rectangle(6.0, 6.0, 7.0, 8.0) 
		);
		double totalArea = driver.calculateTotalArea(allShapes);
		System.out.println("Total area of all shapes = " + totalArea);
	}
	
	public double calculateTotalArea(List<Shape> shapes) {
		double totalArea = 0.0;
		for (Shape shape : shapes) {  // could be an instance of any subclass

		    // When you call the shape's getArea(), the JVM calls the correct method 
			// based on the runtime type of the object
		    totalArea += shape.getArea();
		}
		return totalArea;
	}

}
