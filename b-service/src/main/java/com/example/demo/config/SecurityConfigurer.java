package com.example.demo.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurer  extends WebSecurityConfigurerAdapter  {


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http
		.httpBasic().disable()
		.formLogin().disable()
		.authorizeRequests()
		.anyRequest()
		.authenticated()
		.and()
		.oauth2ResourceServer()
		.jwt();
		// @formatter:on
	}
	
	@Bean
	public CommonsRequestLoggingFilter logFilter() {
		CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(10000);
		filter.setIncludeHeaders(true);
		filter.setAfterMessagePrefix("REQUEST DATA : ");
		return filter;
	}
	  
}
