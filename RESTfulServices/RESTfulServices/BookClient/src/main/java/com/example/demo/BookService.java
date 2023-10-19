package com.example.demo;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class BookService {
	private static final String bookstoreUrl = "http://localhost:8082/recommended";

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * If the circuit breaker is closed, send a request to the Bookstore URL. 
	 * But if the circuit breaker is open, call the private method backupForOpenCircuit.
	 */
	@HystrixCommand(fallbackMethod = "backupForOpenCircuit")
	public String readingList() {
		return restTemplate.getForObject(URI.create(bookstoreUrl), String.class);
	}

	@SuppressWarnings("unused")
	private String backupForOpenCircuit() {
		return "Cloud Native Java (O'Reilly)";
	}

}
