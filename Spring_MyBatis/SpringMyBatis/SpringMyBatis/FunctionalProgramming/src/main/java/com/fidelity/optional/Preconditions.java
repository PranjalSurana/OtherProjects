package com.fidelity.optional;

public class Preconditions {

	/**
	 * Verify that the input string is not null
	 * @param string
	 * @return the string if not null, otherwise an empty string
	 */
	public static String checkNotNull(String string) {
		String str = (string == null) ? "" : string;
		
		return str;
	}

}
