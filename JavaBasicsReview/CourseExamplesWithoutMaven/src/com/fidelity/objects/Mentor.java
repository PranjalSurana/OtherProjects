package com.fidelity.objects;

import java.math.BigDecimal;

public class Mentor {
	private int id;
	private String firstName;
	private String lastName;
	
	// Public Methods
	public BigDecimal calculateWeeklyPay() {
		return BigDecimal.ZERO;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	// Constructors
	public Mentor() {

	} 
	
	public Mentor(int id, String firstName, String lastName) {
		this(firstName, lastName);
		this.id = id;
	}
	
	public Mentor(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	// Getters
	public int getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}

	// Setters
	public void setId(int id) {
		this.id = id;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
