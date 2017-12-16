package com.kidz.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import com.kidz.login.model.Account;
import com.kidz.login.model.Role;
import com.kidz.security.TokenAuthenticationService;
import com.kidz.service.RoleService;
import com.kidz.service.UserService;

import io.jsonwebtoken.Jwts;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@RestController
@RequestMapping(value="/auth")
public class AuthenticationController {

    @Autowired
    private UserService accountService;

    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/register", method= RequestMethod.POST)
    public Account register(@RequestBody Account account){
    	
    	if(accountService.findByUsername(account.getUsername())!=null)
    		throw new RuntimeException("usernameExists");	
    	
        Account createdAccount = accountService.createNewAccount(account);
      
        return createdAccount;
    
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/editUser", method= RequestMethod.POST)
    public Account editUser(@RequestBody Account account){
    	    	
        Account createdAccount = accountService.createNewAccount(account);
      
        return createdAccount;
    
    }
    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/getAllUsers", method= RequestMethod.POST)
    public Page<Account> getAllUsers(Pageable pageable,@RequestBody Map<String, Object> filterMap){

    	return accountService.findAll(pageable,filterMap);
      
        
    }
    
    @RequestMapping(value="/findByUsername", method= RequestMethod.POST)
    public Account findByUsername(@RequestParam String username){

       return accountService.findByUsername(username);
    
    }

    @RequestMapping(value="/findRoleByCode", method= RequestMethod.POST)
    public Role findRoleByCode(@RequestParam String code){

    	return roleService.findByCode(code);
    
    }
    
    @RequestMapping(value="/findRoleById/{roleId}", method= RequestMethod.GET)
    public Role findRoleById(@PathVariable Long roleId){

    	return roleService.findById(roleId);
    
    }

    @RequestMapping(value="/saveRole", method= RequestMethod.PUT)
    public void findRoleById(@RequestBody Role role){

    	roleService.createRole(role);
    
    }
 
    @RequestMapping(value="/getAllRoles", method= RequestMethod.POST)
    public Page<Role> getAllRoles(Pageable pageable,@RequestBody Map<String, Object> filterMap){

    	return roleService.findAll(pageable,filterMap);
      
        
    }
    
    @RequestMapping(method=RequestMethod.POST,value="/getUserDetails")
	public Account refreshToken(HttpServletRequest request, HttpServletResponse response) {
		
    	//get refresh token from request header
    	String token = request.getHeader(TokenAuthenticationService.HEADER_STRING);
        
    	//extract username from refresh token with SECRET KEY  
    	String username=Jwts.parser()
    			.setSigningKey(TokenAuthenticationService.SECRET)
    			.parseClaimsJws(token.replace(TokenAuthenticationService.TOKEN_PREFIX, "")).getBody().getSubject();
		
		return accountService.findByUsername(username);
		
	}
    
}
