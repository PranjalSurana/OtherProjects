package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.Hystrix;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

@EnableCircuitBreaker
@RestController
@SpringBootApplication
public class DemoApplication {
	@Autowired
	private BookService bookService;

	@GetMapping("/to-read")
	public String getReadingList() {
		return bookService.readingList();
	}

	@PreDestroy
	public void close() {
		System.out.println("Resetting Hystrix to stop background thread");
		Hystrix.reset();
	}

	/**
	 * Spring won't create a RestTemplate automatically, so we need to define a bean * method.
	 */
	@Bean
	RestTemplate createRestTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
