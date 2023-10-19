package com.fidelity.services;

public class StringProviderMockImpl implements StringProvider {
	private String s = "mock message";

	@Override
	public String provide() {
		return s;
	}

}
