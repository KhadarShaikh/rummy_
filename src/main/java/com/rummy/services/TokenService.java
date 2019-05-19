package com.rummy.services;

import com.rummy.domain.AccessToken;

/**
 * 
 * @author skkhadar
 *
 */
public interface TokenService {

	/**
	 * 
	 * @param token
	 * @return
	 */
	AccessToken getAccessToken(String token);
}
