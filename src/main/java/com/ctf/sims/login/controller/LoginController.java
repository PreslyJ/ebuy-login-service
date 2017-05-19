package com.ctf.sims.login.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Base64;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ctf.sims.login.model.UserLogin;

@RestController
public class LoginController  {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@SuppressWarnings("rawtypes")
	@ModelAttribute
	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity  redirectToTarget(@ModelAttribute UserLogin userLogin,HttpServletResponse response){
		
		try {
			String enc=userLogin.getUsername()+":"+userLogin.getPassword();
			
			String encodedStr=Base64.getEncoder().encodeToString(enc.getBytes());
			
			byte[] byteText = encodedStr.getBytes(Charset.forName("UTF-8"));
			//To get original string from byte.
			String originalString= new String(byteText , "UTF-8");
			
			
			//Charset.forName("UTF-8").encode(encodedStr);
		/*	
			String safeCookieName = URLEncoder.encode("nginxauth", "UTF-8");
			String safeCookieValue = URLEncoder.encode(encodedStr, "UTF-8");*/
			
			Cookie cookie = new Cookie("nginxauth", originalString);
			cookie.setHttpOnly(true);
   
			log.error("Cookie VAL====>   "+cookie.getValue());
  
			response.addCookie(cookie);
			response.setHeader("Location", userLogin.getTarget());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    
	    return new ResponseEntity(org.springframework.http.HttpStatus.FOUND);

	}

	@SuppressWarnings("rawtypes")
	@CrossOrigin
	@PostMapping("/logout")
	public ResponseEntity  logout(HttpServletRequest request,HttpServletResponse response){

		Cookie[] cookies = request.getCookies();
		if(cookies!=null)
			for (int i = 0; i < cookies.length; i++) {
			 log.error("Cookie VAL==>   "+cookies[0].getValue());	
			 cookies[i].setMaxAge(0);
			 response.addCookie(cookies[i]);
			}
		
		response.setHeader("Location", "/login");
	    
		return new ResponseEntity(org.springframework.http.HttpStatus.FOUND);

	}
	
}
