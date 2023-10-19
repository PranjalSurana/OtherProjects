package com.fidelity.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertyReader {
	@Value("${value}")
	private String value;

	public String getValue() {
		return value;
	}

}
