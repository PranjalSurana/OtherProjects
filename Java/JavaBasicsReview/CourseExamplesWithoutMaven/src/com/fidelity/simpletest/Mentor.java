package com.fidelity.simpletest;


public class Mentor {
	private String firstName;
	private String lastName;
	
	// Public Methods
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	// Constructors
	public Mentor(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
}

