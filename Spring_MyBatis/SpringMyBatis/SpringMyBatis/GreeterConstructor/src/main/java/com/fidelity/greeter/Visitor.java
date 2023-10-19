package com.fidelity.greeter;

public interface Visitor {
	/* Visitor's name */
	public String getName();
	/* Hello, Howdy, Hiya, etc. */
	public String getGreeting();
}
