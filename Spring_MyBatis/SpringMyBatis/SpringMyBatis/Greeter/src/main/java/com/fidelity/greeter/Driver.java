package com.fidelity.greeter;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Driver {

	public static void main(String[] args) {
		String springConfigurationFile = "greeter-beans.xml";
		if (args.length > 0) {
			springConfigurationFile = args[0];
			System.out.println("Using bean configuration " + springConfigurationFile);
		}
		
		AbstractApplicationContext factory = 
				new ClassPathXmlApplicationContext(springConfigurationFile);
		
		// Use factory
		Greeter greeter = factory.getBean("greeter", Greeter.class);
		
		greeter.greet();

		factory.close();
	}
	
	/*
	 * This method shows the plain Java code equivalent to the Spring-based main method
	 * (excluding handling command line arguments).
	 */
	public static void javaVersion() {
		Visitor vis = new AmarilloVisitor();
		// Any setter injection defined for vis

		PopupGreeter greeter = new PopupGreeter();
		greeter.setVisitor(vis);
	}

}
