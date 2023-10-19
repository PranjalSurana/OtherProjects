package com.fidelity.mixed1;


public class RandomSimulator {
	double seed = 999.0;

//	In this version, there are no value annotations
	public void setSeed(double seed) {
		System.out.println("seed: " + seed);
		this.seed = seed;
	}

	public double getSeed() {
		return seed;
	}

}
