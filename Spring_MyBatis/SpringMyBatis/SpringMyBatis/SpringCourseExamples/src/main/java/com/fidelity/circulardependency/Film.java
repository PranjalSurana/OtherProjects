package com.fidelity.circulardependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Film {
	private Budget budget;
	
	@Autowired
	public Film(Budget budget) {
		this.budget = budget;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

}
