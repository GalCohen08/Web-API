package com.web_api.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.web_api.model.User;

@Service
public class AuthenticationServiceImpl implements UserAuthenticationService{
	Map<String, User> users = new HashMap<>();

	@Override
	public Optional<String> login(String username, String password) { 
		final String token = UUID.randomUUID().toString();
	    User user = new User(token, username , password);
	    users.put(token, user);
	    return Optional.of(token);
	}

	@Override
	public Optional<User> findByToken(String token) {
		return Optional.ofNullable(users.get(token));
	}

	@Override
	public void logout(User user) {
		users.remove(user.getId());
		
	}
	
}
