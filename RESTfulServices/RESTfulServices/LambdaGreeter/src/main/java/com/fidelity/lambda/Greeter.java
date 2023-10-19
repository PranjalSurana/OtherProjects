package com.fidelity.lambda;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Greeter {
    public Map<String, Object> handleRequest(
    				Map<String, Object> request, Context context) {
		// request Map keys: version, routeKey, rawPath, rawQueryString, headers, 
    	//                   requestContext, body, isBase64Encoded

    	Map<String, String> headers = Map.of("Content-Type", "application/json");
		try {
			// request body: {"name": "Bullwinkle J. Moose"}
			String body = String.valueOf(request.get("body"));
			context.getLogger().log("Request body: " + body); 

			ObjectMapper objectMapper = new ObjectMapper();
			// convert the JSON in the request body to a JavaBean
			Greetings greetings = objectMapper.readValue(body, Greetings.class);

			return Map.of(
				"headers", headers,
				"statusCode", 200,
				"body", Map.of("message", "Hello " + greetings.getName()) // Map is converted to JSON automatically
			);
    	}
    	catch (JsonProcessingException ex) {
			return Map.of(
				"headers", headers,
				"statusCode", 400,
				"body", Map.of("error", "Problem parsing input: " + ex));
    		
    	}
    }
}
