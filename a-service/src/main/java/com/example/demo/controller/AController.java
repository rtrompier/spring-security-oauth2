package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@RestController
@RequestMapping("/api")
public class AController {

    private final WebClient webClient;

    @Autowired
    public AController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping
//	@PreAuthorize("hasAnyAuthority('formUSER')")
    public Mono<String> getInfo() {
        System.out.println("-------------------------- SERVICE A ---");

//        return ResponseEntity.ok("Hello form Service A");

		return webClient
		        .get()
		        .uri("http://localhost:8082/api")

		        .attributes(clientRegistrationId("library-client"))
		        .retrieve()
		        .onStatus(
		            httpStatus -> HttpStatus.FORBIDDEN.value() == httpStatus.value(),
		            clientResponse -> {
		            	System.out.println("No access for this resource");
						return Mono.error(new AccessDeniedException("No access for this resource"));
					})
		        .onStatus(
		        		HttpStatus::is4xxClientError,
			            clientResponse -> {
							System.out.println("Not Authorized to access to B");
							return Mono.error(new AccessDeniedException("Not Authorized to access to B"));
						})
		        .onStatus(
		            HttpStatus::is5xxServerError,
		            clientResponse -> {
						System.out.println("Internal error occurred");
		            	return Mono.error(new IllegalStateException("Internal error occurred"));
					})
		        .bodyToMono(String.class).map((s) -> {
					System.out.println("A répond: " + s);
					return "A répond: " + s;
				});
    }


}
