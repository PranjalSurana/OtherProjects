package com.fidelity.greeter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
//	@Bean(name="amarilloVis")
//	public Visitor createOldVisitor(){
//		return new AmarilloVisitor();
//	}

	@Bean(name="indiavisitor")
	public Visitor createVisitor() { return new IndiaVisitor(); }

	@Bean(name="greeter")
	public Greeter createGreeter(){
		return new PopupGreeter();
	}

}