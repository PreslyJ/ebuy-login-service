package com.ctf.sims.login.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.ctf.sims.login.security.LDAPServiceUtil;
import com.ctf.sims.login.security.TokenAuthenticationService;
import io.jsonwebtoken.Jwts;

@RestController
public class RefreshTokenController {
    
	@Autowired
	LDAPServiceUtil ldapserviceutil;
	
	
    @RequestMapping(method=RequestMethod.POST,value="/getAuthToken")
    public @ResponseBody String refreshToken(HttpServletRequest request, HttpServletResponse response) {
    	
    	String token = request.getHeader("REFRESH");
        
    	String jti=Jwts.parser()
  		      .setSigningKey(TokenAuthenticationService.SECRET)
  		      .parseClaimsJws(token).getBody().getId();
    	
    	if(jti==null || TokenAuthenticationService.getRedisKey("TOKEN_"+jti)==null )
    		throw new RuntimeException("Invalid JWT TOKEN");
    		
    	String user=Jwts.parser()
	      .setSigningKey(TokenAuthenticationService.SECRET)
	      .parseClaimsJws(token).getBody().getSubject();
    	    	
    	String jwt=TokenAuthenticationService.getAuthToken(user, ldapserviceutil.getGrantedAuthorities(user));
    	
    	response.addHeader(TokenAuthenticationService.HEADER_STRING, jwt);
    	response.addHeader("Access-Control-Expose-Headers","Authorization");
    	
        return "success";

    }
    
    
    
   /* @RequestMapping(method=RequestMethod.POST,value="/logout")
    public @ResponseBody String invalidateToken(HttpServletRequest request, HttpServletResponse response) {
    	
    	System.out.println("OK..........");
    	
    	String token = request.getHeader("REFRESH");
        
    	String jti=Jwts.parser()
  		      .setSigningKey(TokenAuthenticationService.SECRET)
  		      .parseClaimsJws(token).getBody().getId();
    	
    	if(jti==null || TokenAuthenticationService.getRedisKey("TOKEN_"+jti)==null )
    		throw new RuntimeException("Invalid JWT TOKEN");
    		
    	
    	try {
			TokenAuthenticationService.delRedisKey("TOKEN_"+jti);
		} catch (Exception e) {
			e.printStackTrace();
			return "logout failed";
		}
    	
        return "success";

    }*/
    
    
    
}
