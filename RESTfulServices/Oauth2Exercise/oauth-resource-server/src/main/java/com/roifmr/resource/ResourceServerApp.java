package com.roifmr.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
	"com.roifmr.business",
	"com.roifmr.integration",
	"com.roifmr.resource.spring",
	"com.roifmr.resource.web.controller",
})
public class ResourceServerApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ResourceServerApp.class, args);
    }
    
}
