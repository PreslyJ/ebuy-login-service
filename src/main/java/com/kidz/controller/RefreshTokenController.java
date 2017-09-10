package com.kidz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.kidz.security.TokenAuthenticationService;

import io.jsonwebtoken.Jwts;

@RestController
public class RefreshTokenController {
    
	
	
    @RequestMapping(method=RequestMethod.POST,value="/getAuthToken")
    public @ResponseBody String refreshToken(HttpServletRequest request, HttpServletResponse response) {
    	
    	String token = request.getHeader("REFRESH");
        
    	String jti=Jwts.parser()
  		      .setSigningKey(TokenAuthenticationService.SECRET)
  		      .parseClaimsJws(token).getBody().getId();
    	
//    	if(jti==null || TokenAuthenticationService.getRedisKey("TOKEN_"+jti)==null )
 //   		throw new RuntimeException("Invalid JWT TOKEN");
    		
    	String user=Jwts.parser()
	      .setSigningKey(TokenAuthenticationService.SECRET)
	      .parseClaimsJws(token).getBody().getSubject();
    	    	
/*    	String jwt=TokenAuthenticationService.getAuthToken(user, ldapserviceutil.getGrantedAuthorities(user));
    	
    	response.addHeader(TokenAuthenticationService.HEADER_STRING, jwt);
*/    	response.addHeader("Access-Control-Expose-Headers","Authorization");
    	
        return "success";

    }
    
}
