package com.ctf.sims.login;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	
  public JWTLoginFilter(String url, AuthenticationManager authManager) {
    super(new AntPathRequestMatcher(url));
    setAuthenticationManager(authManager);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
    AccountCredentials creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
    /*List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    int i=0;
    while(i<120){
    	authorities.add(new SimpleGrantedAuthority("lajdlkdjslkdjslksajdlksajdlaksjdlksajdlsajdlsajdlahdksagjdkhgasdfsajdfgsadfhgsadhf"));
    	i++;
    }
    */
    Authentication auth=  getAuthenticationManager().authenticate(
            new UsernamePasswordAuthenticationToken(
                creds.getUsername(),
                creds.getPassword(),
                Collections.emptyList()
            )
        );
    
    return auth;
    
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
    TokenAuthenticationService.addAuthentication(res, auth);
  }
}