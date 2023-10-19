package com.fidelity.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BookstoreApplication is both a Spring Boot application class
 * and a REST controller.
 */
@RestController
@SpringBootApplication
public class BookstoreApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookstoreApplication.class, args);
  }

  @GetMapping("/recommended")
  public String getReadingList(){
    return "Spring in Action (Manning), Cloud Native Java (O'Reilly), Learning Spring Boot (Packt)";
  }

  /**
   * This method allows Loggers to be autowired in other classes:
   *    @Autowired private Logger logger;
   */
  @Bean
  @Scope("prototype")
  Logger createLogger(InjectionPoint ip) {
      Class<?> classThatWantsALogger = ip.getField().getDeclaringClass();
      return LoggerFactory.getLogger(classThatWantsALogger);
  }
}
