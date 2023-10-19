package com.fidelity.lambda;

public class Greetings {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Greetings [name=" + name + "]";
	}

}
