package com.fidelity.objects;

public class ObjectExamples {

	@SuppressWarnings("unused")
	public void declareConstructCall() {
		// DECLARE variable of specific class type
		Mentor mentor;

		// CONSTRUCT an object, ASSIGN it
		mentor = new Mentor(); 
		
		// CALL method, save result
		String firstName = mentor.getFirstName();

	}
	
	@SuppressWarnings("unused")
	public void assignmentCall() {
		Mentor mentor = new Mentor("Aadya", "Kumar");
		Mentor assignee = new Mentor("John", "Smith");

		String fullName = mentor.getFullName();  // Aadya Kumar
		fullName = assignee.getFullName();       // John Smith

	}
}
