package com.fidelity.mixed1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	@Value("#{ new java.util.Random().nextDouble() * 100.0 }")
	double seed;

	@Bean("rand1")
	public RandomSimulator getRand1() {
		RandomSimulator r = new RandomSimulator();
		r.setSeed(seed);
		return r;
	}

	@Bean("rand2")
	public RandomSimulator getRand2() {
		RandomSimulator r = new RandomSimulator();
		r.setSeed(seed);
		return r;
	}
	
	@Bean("rand1v")
	public RandomSimulator getRand1ByValueAnnotation(
			@Value("#{ new java.util.Random().nextDouble() * 100.0 }") double seed) {
		RandomSimulator r = new RandomSimulator();
		r.setSeed(seed);
		return r;
	}

	@Bean("rand2v")
	public RandomSimulator getRand2ByValueAnnotation(
			@Value("#{ rand1v.seed }") double seed) {
		RandomSimulator r = new RandomSimulator();
		r.setSeed(seed);
		return r;
	}
}
