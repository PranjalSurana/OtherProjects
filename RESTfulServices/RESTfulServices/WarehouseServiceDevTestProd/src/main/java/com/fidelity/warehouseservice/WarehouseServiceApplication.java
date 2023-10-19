package com.fidelity.warehouseservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * The Spring Boot application that launches the WarehouseController which is a
 * RESTful web service.
 * 
 * @author ROI Instructor Team
 *
 */
@SpringBootApplication
// tell Spring Boot where to scan for annotated components
@ComponentScan(basePackages = { "com.fidelity.integration", 
								"com.fidelity.restcontroller",
								"com.fidelity.business.service" })
// tell MyBatis where to scan for mapping interface files
@MapperScan(basePackages = "com.fidelity.integration.mapper")
public class WarehouseServiceApplication {
	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(WarehouseServiceApplication.class, args);
	}

	/**
	 * This method creates a Logger that can be autowired in other
	 * classes:{@code @Autowired private Logger logger; }
	 */
	@Bean
	@Scope("prototype")
	public Logger createLogger(InjectionPoint ip) {
		Class<?> classThatWantsALogger = ip.getField().getDeclaringClass();
		return LoggerFactory.getLogger(classThatWantsALogger);
	}

	/**
	 * Create a DataSource based on the properties set in the active profile
	 * Currently, the dev profile uses the in memory database
	 * Currently, the test profile uses the Oracle scott database
	 * Currently, the prod profile uses the Oracle hr database
	 * 
	 * @return the DataSource for the database associated with the active profile
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		// set the DataSource properties read from the active profile
		dataSource.setUrl(env.getProperty("app.datasource.url"));
		dataSource.setUsername(env.getProperty("app.datasource.username"));
		dataSource.setPassword(env.getProperty("app.datasource.password"));
		dataSource.setDriverClassName(env.getProperty("app.datasource.driverClassName"));
		
		return dataSource;
	}
}
