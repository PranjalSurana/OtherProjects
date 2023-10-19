## OAuth Authorization Server

This project implements an OAuth2 authorization server with an embedded Keycloak server.

Most of the code is from the excellent Baeldung tutorial [Spring REST API + OAuth2 + Angular](https://www.baeldung.com/rest-api-spring-oauth2-angular)

Interesting classes:
- `KeycloakServerProperties` - sets context page to `/auth`, sets the admin user name and password

Interesting files:
- `application.yml`
    - Server config (port number)
    - Spring JPA config for in-memory database
    - Keycloak config
- `roifmr-realm.json` - Keycloak security realm config, including user IDs

### Additional Articles:

- [Keycloak Embedded in a Spring Boot Application](https://www.baeldung.com/keycloak-embedded-in-spring-boot-app)
- [Accessing Keycloak Endpoints Using Postman](https://www.baeldung.com/postman-keycloak-endpoints)
