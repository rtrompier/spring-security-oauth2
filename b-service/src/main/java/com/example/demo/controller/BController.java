package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class BController {

	@GetMapping
	//@PreAuthorize("hasAnyAuthority('ROLE_USER')")
	public String getInfo(Principal principal) {
		return "je suis dans le service B !!!!";
	}
}
