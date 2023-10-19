package com.fidelity.greeter;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Driver {

	public static void main(String[] args) {
		String springConfigFile = "greeter-beans.xml";

		// TODO: create a bean factory that is configured using the bean configuration
		//       file that is defined above.
		// HINT: see slide 1-14
		BeanFactory factory = new ClassPathXmlApplicationContext("greeter-beans.xml");

			
		// TODO: get the bean with the ID "greeter" from the bean factory
		Greeter greeter =  factory.getBean("greeter", Greeter.class);

		System.out.println("Got greeter " + greeter);

		// TODO: call the Greeter bean's greet() method
		greeter.greet();
		
	}
}
