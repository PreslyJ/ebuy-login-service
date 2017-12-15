package com.kidz.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.kidz.security.CustomUserDetailsService;
import com.kidz.security.TokenAuthenticationService;

import io.jsonwebtoken.Jwts;

@RestController
public class RefreshTokenController {
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
	
    @CrossOrigin
    @RequestMapping(method=RequestMethod.POST,value="/getAuthToken")
    public @ResponseBody String getAccessToken(HttpServletRequest request, HttpServletResponse response) {
    	
    	//get refresh token from request header
    	String token = request.getHeader("refresh");
        
    	//extract username from refresh token with SECRET KEY  
    	String username=Jwts.parser()
    			.setSigningKey(TokenAuthenticationService.SECRET)
    			.parseClaimsJws(token).getBody().getSubject();
    	    	
    	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    	
    	//get a new access token for user by passing access authorities 
    	String jwt=TokenAuthenticationService.getAuthToken(username,userDetails.getAuthorities());
    	
    	//bind the access token to header
    	response.addHeader(TokenAuthenticationService.HEADER_STRING, jwt);
    	
    	response.setHeader("Access-Control-Allow-Origin","*");
    	response.setHeader("Access-Control-Allow-Credentials", "true");
    	response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
    	response.setHeader("Access-Control-Allow-Headers", "REFRESH,Access-Control-Allow-Origin,Access-Control-Allow-Methods,Access-Control-Allow-Credentials,Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,Vary");
    	response.addHeader("Access-Control-Expose-Headers","Authorization,REFRESH");
    	
        return "success";

    }
    
}
