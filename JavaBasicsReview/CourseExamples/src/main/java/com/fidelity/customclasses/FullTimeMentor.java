package com.fidelity.customclasses;

public class FullTimeMentor extends Mentor {
	@SuppressWarnings("unused")
	private double payPerWeek;
		
	public FullTimeMentor(String firstName, String lastName, double payPerWeek) {
		super(firstName, lastName);
		this.payPerWeek = payPerWeek;
	}

	// From FullTimeMentor class
	@Override
	public double calculatePay() {
		return 0.0;
	}

}