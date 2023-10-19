package com.fidelity.mixed2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:mixed2-beans.xml")
public class AppConfig {
	@Value("#{ new java.util.Random().nextDouble() * 100.0 }")
	double seed;
	
	@Autowired
	@Qualifier("rand2")
	RandomSimulator rand3;

	@Bean("rand1")
	public RandomSimulator getRand1() {
		RandomSimulator r = new RandomSimulator();
		r.setSeed(seed);
		return r;
	}

	// This version will not be used since XML "always wins"
	@Bean("rand2")
	public RandomSimulator getRand2() {
		RandomSimulator r = new RandomSimulator();
		r.setSeed(999.0);
		return r;
	}
	
	@Bean("rand3")
	public RandomSimulator getRand3() {
		return rand3;
	}
}
