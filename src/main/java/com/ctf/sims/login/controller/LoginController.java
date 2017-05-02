package com.ctf.sims.login.controller;


import java.util.Base64;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ctf.sims.login.model.UserLogin;

@RestController
public class LoginController  {
	
	@SuppressWarnings("rawtypes")
	@ModelAttribute
	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity  redirectToTarget(@ModelAttribute UserLogin userLogin,HttpServletResponse response){
		
		String enc=userLogin.getUsername()+":"+userLogin.getPassword();
		
		String encodedStr=Base64.getEncoder().withoutPadding().encodeToString(enc.getBytes());
		
		Cookie cookie = new Cookie("nginxauth", encodedStr);
	    cookie.setHttpOnly(true);
	   
	    response.addCookie(cookie);
	    response.setHeader("Location", userLogin.getTarget());
	  
	    
	    return new ResponseEntity(org.springframework.http.HttpStatus.FOUND);

	}

	@SuppressWarnings("rawtypes")
	@CrossOrigin
	@PostMapping("/logout")
	public ResponseEntity  logout(HttpServletRequest request,HttpServletResponse response){

		Cookie[] cookies = request.getCookies();
		if(cookies!=null)
			for (int i = 0; i < cookies.length; i++) {
			 cookies[i].setMaxAge(0);
			 response.addCookie(cookies[i]);
			}
		
		response.setHeader("Location", "/login");
	    
		return new ResponseEntity(org.springframework.http.HttpStatus.FOUND);

	}
	
}
