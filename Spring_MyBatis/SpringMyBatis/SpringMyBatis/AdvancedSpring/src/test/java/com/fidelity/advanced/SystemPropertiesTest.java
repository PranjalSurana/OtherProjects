package com.fidelity.advanced;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class SystemPropertiesTest {
	private AbstractApplicationContext factory;
	private SystemProperties propHost;

	@BeforeEach
	void setUp() {
		String springConfigurationFile = "spel-beans.xml";
		factory = new ClassPathXmlApplicationContext(springConfigurationFile);
		propHost = factory.getBean("props", SystemProperties.class);
	}
	
	@AfterEach
	void tearDown() {
		factory.close();
	}
	
	@Test
	void listSystemProperties() {
		System.out.println("\n\nSystem Properties");
		Properties props = System.getProperties();
		Set<String> sortedKeys = new TreeSet<>(props.stringPropertyNames());
		for(String key : sortedKeys) {
			System.out.println(key + ": " + props.getProperty(key));
		}
	}

	@Test
	void testSystemProperties() {
		// Change this to your country
		assertEquals("GB", propHost.getCountry());
	}

	@Test
	void listEnvironment() {
		System.out.println("\n\nEnvironment");
		Map<String, String> envSorted = new TreeMap<>(System.getenv());
		for(String key : envSorted.keySet()) {
			System.out.println(key + ": " + envSorted.get(key));
		}
	}

	@Test
	void testEnvironment() {
		// Change this to your country
		assertEquals("C:\\WINDOWS", propHost.getWinDir());
	}


}
