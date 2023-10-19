package com.fidelity.failingtest;

@SuppressWarnings("unused")
public class Mentor {
	private String firstName;
	private String lastName;
	
	// Public Methods	
	public String getFullName() {
		return "Aadya Kumar";
	}
	
	// Constructors
	public Mentor(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
