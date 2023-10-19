package com.fidelity.exceptions;

public class Mentor {
	private String firstName;
	private String lastName;
	private int id;

	
	// Public Methods
	public String getFullName() {
		return firstName + " " + lastName;
	}


	// Constructors
	public Mentor(String firstName, String lastName, int id) {
		if (id < 0) {
			throw new IllegalArgumentException("id may not be negative");
		}
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	// Getters and setters
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getId() {
		return id;
	}

}

