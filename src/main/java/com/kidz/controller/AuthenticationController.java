package com.kidz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import com.kidz.model.Account;
import com.kidz.service.UserService;


@RestController
public class AuthenticationController {

    @Autowired
    private UserService accountService;


    @RequestMapping(value="/register", method= RequestMethod.POST)
    public ResponseEntity<Account> register(@RequestBody Account account){

        Account createdAccount = accountService.createNewAccount(account);
      
        return new ResponseEntity<Account>(createdAccount, HttpStatus.CREATED);
    
    }


}
