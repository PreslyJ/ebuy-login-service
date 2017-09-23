package com.kidz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import com.kidz.model.Account;
import com.kidz.model.Role;
import com.kidz.service.RoleService;
import com.kidz.service.UserService;


@RestController
@RequestMapping(value="/auth")
public class AuthenticationController {

    @Autowired
    private UserService accountService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value="/register", method= RequestMethod.POST)
    public Account register(@RequestBody Account account){

        Account createdAccount = accountService.createNewAccount(account);
      
        return createdAccount;
    
    }

    @RequestMapping(value="/getAllUsers", method= RequestMethod.POST)
    public Page<Account> getAllUsers(Pageable pageable){

    	return accountService.findAll(pageable,null);
      
        
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
    
}
