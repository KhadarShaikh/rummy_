package com.rummy.util;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/*
 * implemented customuserdetails 
 * 
 * author s.k.sharma
 * 
 * 10/7/18
 */
/**
 * 
 * @author vraghuram
 *
 */
public class CustomUserDetails implements UserDetails {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	/**
	 * userEmail
	 */
	
	@SuppressWarnings("unused")
	private String userEmail;
	private String userPassword;
	
	public CustomUserDetails(String userEmail, String userPassword) {
		super();
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.userPassword;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userEmail;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
