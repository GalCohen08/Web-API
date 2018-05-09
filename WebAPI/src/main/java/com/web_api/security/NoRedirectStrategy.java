package com.web_api.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.RedirectStrategy;

public class NoRedirectStrategy implements RedirectStrategy{
	
	@Override
	public void sendRedirect(final HttpServletRequest request, final HttpServletResponse response, final String url) throws IOException {}
}
