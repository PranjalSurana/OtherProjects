package com.fidelity.controller;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fidelity.domain.MessageServiceResponse;

@RestController
public class MessageClientController {
	private static final int HOVERFLY_PORT = 8500;
	private static final String HOVERFLY_HOST = "localhost";
	private static final int MESSAGE_SERVER_PORT = 9080;
	private static final String MESSAGE_SERVER_HOST = "localhost";
	private static final String mode = "proxy"; // proxy or production

	@GetMapping("/invoke")
	public MessageServiceResponse getMessage() {
		String url = "http://" + MESSAGE_SERVER_HOST + ":" + MESSAGE_SERVER_PORT + "/message";
		MessageServiceResponse response = null;
		
		try {
			RestTemplate restTemplate = restTemplate();
			response = restTemplate.exchange(url, HttpMethod.GET, null, MessageServiceResponse.class)
								   .getBody();
		}
		catch(Exception ex) {
			RestTemplate restTemplate = new RestTemplate();
			response = restTemplate.exchange(url, HttpMethod.GET, null, MessageServiceResponse.class)
					   .getBody();
		}

		return response;
	}

	private RestTemplate restTemplate() {
		RestTemplate template = null;

		if (mode.equalsIgnoreCase("proxy")) {
			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(HOVERFLY_HOST, HOVERFLY_PORT));
			requestFactory.setProxy(proxy);
			template = new RestTemplate(requestFactory);
		} else {
			template = new RestTemplate();
		}

		return template;
	}
}
