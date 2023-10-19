//package com.fidelity.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()
//			.antMatchers("/css/**", "/index").permitAll()	// everyone can access /index and css files
//			.antMatchers("/warehouse/**").hasRole("USER")	// warehouse resources require USER role
//			.and()
//			.httpBasic();									// use HTTP Basic authentication
//	}
//
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//			.inMemoryAuthentication()
//			.withUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER"));
//	}
//}
