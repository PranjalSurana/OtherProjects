package com.fidelity.business;

import java.math.BigDecimal;

/*
 * Notice that this version of Mentor has a very different definition. In particular, there is only a fullName
 * rather than a firstName and lastName.
 */
public class Mentor {
	private int id;
	private String fullName;
	
	// Public Methods
	public BigDecimal calculateWeeklyPay() {
		return BigDecimal.ZERO;
	}
	
	// Constructors
	public Mentor() {

	} 
	
	public Mentor(String fullName) {
		this.fullName = fullName;
	}
	
	// Getters
	public int getId() {
		return id;
	}
	public String getFullName() {
		return fullName;
	}

}
