package com.fidelity.cipher.tools;

public class ModularArithmetic {

	private String key;
	private int base;

	// Keys are assumed to be strings of digits
	public ModularArithmetic(String key) {
		this(key, 10);
	}

	public ModularArithmetic(String key, int base) {
		this.key = key;
		this.base = base;
	}

	public String add(String message) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < message.length(); i++) {
			sb.append(Integer.toString(
					 (Character.getNumericValue(message.charAt(i)) 
					+ Character.getNumericValue(key.charAt(i % key.length()))
					) % base));
		}
		return sb.toString();
	}

	/*
	 * Although this looks rather odd, consider that you can add any number of
	 * multiples of base to the number because we are evaluating it modulo base
	 * (in other words, base is equivalent to zero). Another way to think of it
	 * is that we are adding in the borrow (even when it is not needed).
	 */
	public String subtract(String message) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < message.length(); i++) {
			sb.append(Integer.toString((base + 
					 (Character.getNumericValue(message.charAt(i)) 
					- Character.getNumericValue(key.charAt(i % key.length()))
					)) % base));
		}
		return sb.toString();
	}

	public String getKey() {
		return key;
	}

}
