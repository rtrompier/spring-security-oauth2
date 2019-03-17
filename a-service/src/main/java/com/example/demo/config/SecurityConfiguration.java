package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityWebFilterChain configure(ServerHttpSecurity http) {
//        http.authorizeExchange().anyExchange().authenticated().and().oauth2Login().and().oauth2Client();
        http
                .authorizeExchange()
//                .pathMatchers("/actuator/**").permitAll()
//                .pathMatchers("/api/**").authenticated()
                .anyExchange().permitAll()
                .and()
                .oauth2Client()
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }
}