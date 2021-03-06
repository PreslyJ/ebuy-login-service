package com.kidz.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TokenAuthenticationService {
  static long EXPIRATIONTIME = 864_000_000; // 10 days
  public static String SECRET = "ThisIsASecret";
  public static String TOKEN_PREFIX = "Bearer";
  public static String HEADER_STRING = "Authorization";
  
  static void addAuthentication(HttpServletResponse res, Authentication auth) {
   
      String JWT =  getAuthToken(auth.getName(),auth.getAuthorities());
    		  
      Claims refreshClaim = Jwts.claims().setSubject(auth.getName());
      refreshClaim.put("scopes", Arrays.asList("ROLE_REFRESH_TOKEN"));
      
      String jti=UUID.randomUUID().toString();   
      
      String refreshToken = Jwts.builder()
    	        .setClaims(refreshClaim)
    	        .setId(jti)
    	        .setIssuedAt(new Date(System.currentTimeMillis()))
    	        .setExpiration(new Date(System.currentTimeMillis() + 172_800_000)) //2 days for refresh token
    	        .signWith(SignatureAlgorithm.HS512, SECRET)
    	      .compact();
      
      res.addHeader(HEADER_STRING, JWT);
      res.addHeader("REFRESH",refreshToken);
      res.addHeader("Access-Control-Expose-Headers","Authorization,REFRESH");
	  try {
		  res.setContentType("application/json");
		  
		  Map<String, String> tokenMap = new HashMap<String, String>();
	      tokenMap.put("status", "success");
	      new ObjectMapper().writeValue(res.getWriter(), tokenMap);
		  
	  } catch (IOException e) {
		  e.printStackTrace();
	  }
  }

  static Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    if (token != null) {
      
      // parse the token.
      String user = Jwts.parser()
          .setSigningKey(SECRET)
          .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
          .getBody()
          .getSubject();

      List<?> roles   = Jwts.parser()
              .setSigningKey(SECRET)
              .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
              .getBody()
              .get("roles",List.class);
      
      Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
      
      if (null != roles) {
          ArrayList<GrantedAuthority> authsList = new ArrayList<>(roles.size());
          for (Object role : roles) {
              authsList.add(new SimpleGrantedAuthority(role.toString().replace("authority=","").replace("{", "").replace("}","")));
          }
          authorities = Collections.unmodifiableList(authsList);
      } else 
          authorities = Collections.emptyList();
      
      
      return user != null ?
          new UsernamePasswordAuthenticationToken(user, null, authorities) :
          null;
    }
    return null;
  }
  
  public static String getAuthToken(String username,Collection<? extends GrantedAuthority> authos){
	 
	  Claims claims = Jwts.claims().setSubject(username);
	  claims.put("roles",authos);

      return TOKEN_PREFIX + " " + Jwts.builder()
              .setClaims(claims)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
              .signWith(SignatureAlgorithm.HS512, SECRET)
              .compact();
  }
  
}