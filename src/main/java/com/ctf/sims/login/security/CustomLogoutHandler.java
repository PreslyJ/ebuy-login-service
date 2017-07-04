package com.ctf.sims.login.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

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
        
		try {
			
			  response.setContentType("application/json");
			  
			  Map<String, String> tokenMap = new HashMap<String, String>();
		      tokenMap.put("status", "success");
		      new ObjectMapper().writeValue(response.getWriter(), tokenMap);
				  
		} catch (IOException e) {
			  e.printStackTrace();
		}
		
    }
	
}