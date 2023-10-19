package com.fidelity.customclasses;

public class PartTimeMentor extends Mentor {

	public PartTimeMentor(String firstName, String lastName) {
		super(firstName, lastName);
	}

	public PartTimeMentor(String firstName, String lastName, int id) {
		super(firstName, lastName, id);
	}

	@Override
	public String getFullName() {
		return super.getFullName() + " (" + getId() + ")";
	}

}
