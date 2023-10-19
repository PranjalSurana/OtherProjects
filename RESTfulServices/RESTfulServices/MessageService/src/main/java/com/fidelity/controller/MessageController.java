package com.fidelity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fidelity.domain.MessageServiceResponse;

@RestController
public class MessageController {
	@GetMapping("/message")
	public MessageServiceResponse getMessage() {
		String message = "Very important message: the answer is 42";
		MessageServiceResponse response = new MessageServiceResponse(message);
		
		return response;	
	}
}
