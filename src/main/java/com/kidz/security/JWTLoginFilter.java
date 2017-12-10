package com.kidz.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kidz.dto.AccountCredentials;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	
	  public JWTLoginFilter(String url, AuthenticationManager authManager) {
		    super(new AntPathRequestMatcher(url));
		    setAuthenticationManager(authManager);
	  }

	  @Override
	  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
	
			res.setHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));
		    res.setHeader("Access-Control-Allow-Credentials", "true");
		    res.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
		    res.setHeader("Access-Control-Allow-Headers", "REFRESH,Access-Control-Allow-Origin,Access-Control-Allow-Methods,Access-Control-Allow-Credentials,Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,Vary");
			
		  	
			if(req.getContentLength()!=-1)
			{		
				AccountCredentials creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);	    
				
					
				Authentication auth=  getAuthenticationManager().authenticate(
			            new UsernamePasswordAuthenticationToken(
			                creds.getUsername(),
			                creds.getPassword(),
			                Collections.emptyList()
			            )
			        );
			    
				return auth;
			}
			return null;
	  }
	
	  @Override
	  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
		  	TokenAuthenticationService.addAuthentication(res, auth);
	  }

}