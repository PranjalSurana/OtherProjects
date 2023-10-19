package com.fidelity.customclasses;

public class Mentor {
	private String firstName;
	private String lastName;
	private int id;
	private int numberOfDays;
	private double dayRate;
	
	// Public Methods
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public double calculatePay() {
		return calculatePay(0);
	}

	public double calculatePay(double bonus) {
		double pay = 0;
		// perform pay calculation
		pay = dayRate * numberOfDays + bonus;
		return pay;
	}

	// Constructors
	public Mentor(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Mentor(String firstName, String lastName, int id) {
		this(firstName, lastName);
		this.id = id;
	}

	// Getters and setters
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

}

