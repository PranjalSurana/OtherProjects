package com.fidelity.weather.restcontroller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

/**
 * IMPORTANT: be sure to run the database initialization scripts in SQL Developer
 * before running these test cases:
 *	 src/main/resources/schema.sql
 *	 src/main/resources/data.sql
 */

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class WeatherControllerE2eTest {

}
