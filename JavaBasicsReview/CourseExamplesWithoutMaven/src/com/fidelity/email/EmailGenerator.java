package com.fidelity.email;

public class EmailGenerator {

	// return fidelity based email address from the given employee name
	public String makeEmailFromName(String name) {
		String trimmedName = name.trim();
		int spaceIndex = trimmedName.indexOf(' ');
		return trimmedName.substring(spaceIndex + 1).trim().toLowerCase() 
				+ "."
				+ trimmedName.substring(0, spaceIndex).toLowerCase() 
				+ "@fidelity.com";
	}
}