package com.roifmr.resource.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()  // enable CORS
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/warehouse/widgets/**")
            .hasAuthority("SCOPE_read")
            .antMatchers(HttpMethod.POST, "/warehouse/widgets")
            .hasAuthority("SCOPE_write")
            .antMatchers(HttpMethod.PUT, "/warehouse/widgets")
            .hasAuthority("SCOPE_write")
            .antMatchers(HttpMethod.DELETE, "/warehouse/widgets")
            .hasAuthority("SCOPE_write")
            .antMatchers(HttpMethod.GET, "/warehouse/gadgets/**")
            .hasAuthority("SCOPE_read")
            .antMatchers(HttpMethod.POST, "/warehouse/gadgets")
            .hasAuthority("SCOPE_write")
            .antMatchers(HttpMethod.PUT, "/warehouse/gadgets")
            .hasAuthority("SCOPE_write")
            .antMatchers(HttpMethod.DELETE, "/warehouse/gadgets")
            .hasAuthority("SCOPE_write")
            .anyRequest()
            .authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt();
        return http.build();
    }
}
