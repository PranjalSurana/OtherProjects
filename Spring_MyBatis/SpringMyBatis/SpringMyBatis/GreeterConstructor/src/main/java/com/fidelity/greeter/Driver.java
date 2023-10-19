package com.fidelity.greeter;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Driver {

	public static void main(String[] args) {
		String springConfigurationFile = "greeter-beans.xml";
		if ( args.length > 0 ){
			springConfigurationFile = args[0];
			System.out.println("Using bean configuration " + springConfigurationFile);
		}
		AbstractApplicationContext factory = 
				new ClassPathXmlApplicationContext(springConfigurationFile);
		factory.registerShutdownHook();
		// Use factory
		Greeter greeter = factory.getBean("greeter", Greeter.class);
		greeter.greet();

		factory.close();
	}
}
