package com.web_api.controller;


import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web_api.model.User;
import com.web_api.service.UserAuthenticationService;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
public class SecuredUsersController {
	
	@NonNull
	@Autowired
	UserAuthenticationService authentication;

	  @GetMapping("/current")
	  User getCurrent(@AuthenticationPrincipal final User user) {
	    return user;
	  }

	  @GetMapping("/logout")
	  boolean logout(@AuthenticationPrincipal final User user) {
	    authentication.logout(user);
	    return true;
	  }
}
