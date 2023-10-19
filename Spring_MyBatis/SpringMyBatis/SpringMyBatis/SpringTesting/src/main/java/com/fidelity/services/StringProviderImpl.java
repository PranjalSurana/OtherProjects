package com.fidelity.services;

import org.springframework.stereotype.Component;

@Component("stringProvider")
public class StringProviderImpl implements StringProvider {
	private String s = "message";

	@Override
	public String provide() {
		return s;
	}

}
