package com.fidelity.dependency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
// Automatically discover the Film bean (could be define here)
@ComponentScan("com.fidelity.dependency")
@PropertySource("classpath:filmtitle.properties")
public class AppConfig {

	@Bean("chopra")
	public Actor getChopra() {
		return new Actor("Priyanka", "Chopra");
	}
	
	@Bean("depp")
	public Actor getDepp() {
		return new Actor("Johnny", "Depp");
	}
	
	@Bean("hanks")
	public Actor getHanks() {
		return new Actor("Tom", "Hanks");
	}
	
	@Bean("otoole")
	public Actor getOtoole() {
		return new Actor("Peter", "O'Toole");
	}
	
	@Bean("jing")
	public Actor getJing() {
		return new Actor("Jing", "Tian");
	}
	
	// Enable ${...} in @Value annotations
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
