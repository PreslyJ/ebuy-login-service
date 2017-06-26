package com.ctf.sims.login;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

class TokenAuthenticationService {
  static long EXPIRATIONTIME = 864_000_000; // 10 days
  static String SECRET = "ThisIsASecret";
  static String TOKEN_PREFIX = "Bearer";
  static String HEADER_STRING = "Authorization";

  static void addAuthentication(HttpServletResponse res, Authentication auth) {
   
	  Claims claims = Jwts.claims().setSubject(auth.getName());
	  claims.put("roles",auth.getAuthorities());

      String JWT =  Jwts.builder()
              .setClaims(claims)
              .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
              .signWith(SignatureAlgorithm.HS512, SECRET)
              .compact();
      
      res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	  
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
}