package com.fidelity.classes;

import com.fidelity.simpletest.Mentor;

public class ClassUsage {

	public static Object useClasses() {
		Object anything = new Mentor("John", "Smith");
		// sometime later
		anything = "Hello World";
		return anything;
	}
}
