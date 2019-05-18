package com.rummy.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.rummy.domain.Role;
import com.rummy.domain.RoleMapping;
import com.rummy.domain.UserAccount;
import com.rummy.services.RoleMappingService;
import com.rummy.services.RoleService;
import com.rummy.services.UserService;

/**
 * 
 * @author msunil
 *
 */
public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleMappingService roleMappingService;

	@Autowired
	private RoleService roleService;

	Logger logger = Logger.getLogger(AuthenticationTokenProcessingFilter.class);

	public AuthenticationTokenProcessingFilter(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = this.getAsHttpRequest(request);
		logger.debug("http URL requested..");
		System.out.println("request URL : " + httpRequest.getRequestURL().toString());
		if (!httpRequest.getRequestURL().toString().contains("/authenticate")) {
			String accessToken = this.extractAccessTokenFromRequest(httpRequest);
			if (null != accessToken) {
				logger.debug("accessToken extracted..");
				UserAccount token = this.userService.getUserByToken(accessToken);

				List<RoleMapping> mappingList = roleMappingService.findOneRoleMappingByUserId(token.get_id());
				logger.debug("finding roleMapping..");
				List<Role> roles = new ArrayList<Role>();
				for (RoleMapping mapping : mappingList) {
					Map<String, Object> rolecondition = new HashMap<String, Object>();
					rolecondition.put("_id", mapping.getRole_id());
					Role findPojo = roleService.findOneByCondition(rolecondition);
					roles.add(findPojo);
					logger.debug("role created with roleMapping..");
				}
				if (null != token) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(token,
							roles);
					logger.debug("Authenticating user..");
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
			chain.doFilter(request, response);
		}
	}

	private HttpServletRequest getAsHttpRequest(ServletRequest request) {
		if (!(request instanceof HttpServletRequest)) {
			logger.error("Expecting an HTTP request..");
			throw new RuntimeException("Expecting an HTTP request");
		}
		return (HttpServletRequest) request;
	}

	private String extractAccessTokenFromRequest(HttpServletRequest httpRequest) {
		/* Get token from header */
		String authToken = httpRequest.getHeader("X-Access-Token");
		logger.debug("Get token from header..");
		/* If token not found get it from request parameter */
		if (authToken == null) {
			logger.debug(" If token not found get it from request parameter..");
			authToken = httpRequest.getParameter("token");
		}

		return authToken;
	}
}
