## OAuth Resource Server

This is the WarehouseService with requests authenticated using OAuth2.

Most of the code is from the excellent Baeldung tutorial [Spring REST API + OAuth2 + Angular](https://www.baeldung.com/rest-api-spring-oauth2-angular)

Interesting classes:
- `com.roifmr.resource.ResourceServerApp` - Spring Boot app
- `com.roifmr.resource.spring.SecurityConfiguration` - Configures the security for individual HTTP requests
- `com.roifmr.resource.web.controller.WarehouseService` - REST endpoint. Enables CORS for Angular client

Additional security config is in `application.yml`
