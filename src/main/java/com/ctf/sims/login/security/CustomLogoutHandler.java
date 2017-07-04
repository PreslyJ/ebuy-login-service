package com.ctf.sims.login.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import io.jsonwebtoken.Jwts;

@Configuration
public class CustomLogoutHandler implements LogoutHandler {
   
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)  {
    	
    	String token = request.getHeader("REFRESH");
        
    	String jti=Jwts.parser()
  		      .setSigningKey(TokenAuthenticationService.SECRET)
  		      .parseClaimsJws(token).getBody().getId();
    	
    	if(jti==null || TokenAuthenticationService.getRedisKey("TOKEN_"+jti)==null )
    		throw new RuntimeException("Invalid JWT TOKEN");
    	
		TokenAuthenticationService.delRedisKey("TOKEN_"+jti);
        
    }
	
}