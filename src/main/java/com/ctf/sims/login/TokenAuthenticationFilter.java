package com.ctf.sims.login;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;

class TokenAuthenticationService {
  static long EXPIRATIONTIME = 864_000_000; // 10 days
  static String SECRET = "ThisIsASecret";
  static String TOKEN_PREFIX = "Bearer";
  static String HEADER_STRING = "Authorization";

  static void addAuthentication(HttpServletResponse res, Authentication auth) {
   /* String JWT = Jwts.builder()
        .setSubject(auth.getName())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
    res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);*/
	  
	  Claims claims = Jwts.claims().setSubject(auth.getName());
      claims.put("roles",auth.getAuthorities());

      String JWT =  Jwts.builder()
              .setClaims(claims)
              .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
              .signWith(SignatureAlgorithm.HS512, SECRET)
              .compact();
      
      res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	  
  }

  @SuppressWarnings("unchecked")
  static Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    if (token != null) {
      // parse the token.
      String user = Jwts.parser()
          .setSigningKey(SECRET)
          .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
          .getBody()
          .getSubject();

      Collection<? extends GrantedAuthority> authorities  = (Collection<? extends GrantedAuthority>) Jwts.parser()
              .setSigningKey(SECRET)
              .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
              .getBody()
              .get("roles");
      
      return user != null ?
          new UsernamePasswordAuthenticationToken(user, null, authorities) :
          null;
    }
    return null;
  }
}