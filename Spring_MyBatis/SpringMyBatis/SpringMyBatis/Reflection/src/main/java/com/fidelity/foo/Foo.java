package com.fidelity.foo;

public class Foo {
	private String message = "Hello Reflection World";

	public Foo(String msg) {
		message = msg;
	}

	@SuppressWarnings("unused")
	private String bar() {
		return message;
	}
	
	public String getMessage( ) {
		return message;
	}
}
