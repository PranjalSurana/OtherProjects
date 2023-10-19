package com.fidelity.services;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(ApplicationCommonConfig.class)
public class ApplicationConfig {

}
