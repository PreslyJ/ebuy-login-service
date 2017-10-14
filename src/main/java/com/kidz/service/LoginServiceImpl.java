package com.kidz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kidz.login.model.Account;
import com.kidz.rpc.LoginService;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	UserService userService;
	
	@Override
	public boolean authenticateUser(Account account) {
	
	  	if (account.getPassword()== null)
	  		return false;
		
		Account ac= userService.findByUsername(account.getUsername());
		  	
	  	if(ac==null)
			return false;

	  	boolean isPwdMatch=new BCryptPasswordEncoder().matches(  account.getPassword(),ac.getPassword());
	  	
	  	if(isPwdMatch)
	  		return true;
	  		
	  	return false;
	  	
	}



}
