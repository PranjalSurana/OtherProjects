package com.fidelity.properties;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:props-beans.xml")
class PropertyReaderTest {
	@Autowired
	private PropertyReader pr;

	@Test
	@EnabledIfEnvironmentVariable(named="target", matches="prod")
	void testValueProd() {
		assertEquals("hello prod", pr.getValue());
	}

	@Test
	@EnabledIfEnvironmentVariable(named="target", matches="dev")
	void testValueDev() {
		assertEquals("hello dev", pr.getValue());
	}

	@Test
	@DisabledIfEnvironmentVariable(named="target", matches="prod|dev")
	void testValueElse() {
		fail("Need target environment variable set to dev or prod");
	}



}
