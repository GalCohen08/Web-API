package com.web_api.controller;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web_api.service.UserAuthenticationService;

import javax.servlet.http.HttpServletRequest;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)

public class PublicUsersController {
	@NonNull
	@Autowired
	UserAuthenticationService authentication;
	
	@PostMapping("/public/users/login")String login(final HttpServletRequest request, @RequestParam("username") final String username, @RequestParam("password") final String password) {
		return authentication.login(username, password).orElseThrow(() -> new RuntimeException("invalid login and/or password"));
	}
}
