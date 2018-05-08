package com.web_api.service;

import java.util.Optional;

import com.web_api.model.User;

public interface UserAuthenticationService {
	  
	Optional<String> login(String username, String password);
	 
	  Optional<User> findByToken(String token);
	  
	  void logout(User user);
}
