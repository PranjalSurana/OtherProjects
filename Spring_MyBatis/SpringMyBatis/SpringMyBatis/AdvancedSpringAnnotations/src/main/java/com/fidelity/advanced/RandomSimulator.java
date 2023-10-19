package com.fidelity.advanced;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("rand1")
public class RandomSimulator {
	double seed = 999.0;

//	@Value("#{ T(java.lang.Math).random() * 100.0 }")
	@Value("#{ new java.util.Random().nextDouble() * 100.0 }")
	public void setSeed(double seed) {
		System.out.println("seed: " + seed);
		this.seed = seed;
	}

	public double getSeed() {
		return seed;
	}

}
