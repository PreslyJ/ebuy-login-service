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
    	String token = request.getHeader("REFRESH");
        
    	//extract username from refresh token with SECRET KEY  
    	String username=Jwts.parser()
    			.setSigningKey(TokenAuthenticationService.SECRET)
    			.parseClaimsJws(token).getBody().getSubject();
    	    	
    	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    	
    	//get a new access token for user by passing access authorities 
    	String jwt=TokenAuthenticationService.getAuthToken(username,userDetails.getAuthorities());
    	
    	//bind the access token to header
    	response.addHeader(TokenAuthenticationService.HEADER_STRING, jwt);
    	
        return "success";

    }
    
}
