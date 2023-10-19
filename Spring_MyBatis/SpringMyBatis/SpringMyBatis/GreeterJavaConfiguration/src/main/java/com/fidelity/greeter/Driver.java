package com.fidelity.greeter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Driver {
	public static void main(String args[]){
		AbstractApplicationContext factory = 
				new AnnotationConfigApplicationContext(AppConfig.class);

		Greeter greeter = factory.getBean("greeter", Greeter.class);

		System.out.println("Got greeter " + greeter);

		greeter.greet();

		factory.close();	
	}

}
