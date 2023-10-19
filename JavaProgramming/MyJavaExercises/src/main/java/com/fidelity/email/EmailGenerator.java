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
    
    public static void main(String[] args) {
    	EmailGenerator eg = new EmailGenerator();
    	
    	// In all cases, expected output is doe.jane@fidelity.com
    	System.out.println(eg.makeEmailFromName("Jane Doe"));
    	System.out.println(eg.makeEmailFromName("Jane   Doe"));
    	System.out.println(eg.makeEmailFromName("  Jane Doe"));
    	System.out.println(eg.makeEmailFromName("Jane Doe   "));

    	// In this case, expected output is o'malley.frank@fidelity.com
    	System.out.println(eg.makeEmailFromName("Frank O'Malley"));
    }
}
