package com.rummy.util;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
/*
 * 
 * Authentication manager implementation
 * 
 * author s.k.sharma
 * 
 * 10/7/18
 */
public class AuthenticationManagerImpl implements AuthenticationManager {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	
		
	

}
