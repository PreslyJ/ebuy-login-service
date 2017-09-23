package com.kidz.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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
	
    @RequestMapping(method=RequestMethod.POST,value="/getAuthToken")
    public @ResponseBody String refreshToken(HttpServletRequest request, HttpServletResponse response) {
    	
    	String token = request.getHeader("REFRESH");
            	
    	String username=Jwts.parser()
    			.setSigningKey(TokenAuthenticationService.SECRET)
    			.parseClaimsJws(token).getBody().getSubject();
    	    	
    	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    	
    	String jwt=TokenAuthenticationService.getAuthToken(username,userDetails.getAuthorities());
    	
    	response.addHeader(TokenAuthenticationService.HEADER_STRING, jwt);
    	response.addHeader("Access-Control-Expose-Headers","Authorization");
    	
        return "success";

    }
    
}
