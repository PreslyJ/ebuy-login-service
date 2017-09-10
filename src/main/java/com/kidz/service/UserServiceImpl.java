package com.kidz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kidz.model.Role;
import com.kidz.model.Account;
import com.kidz.repository.UserRepository;

import javax.persistence.EntityExistsException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository accountRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public Collection<Account> findAll() {
        Collection<Account> accounts = accountRepository.findAll();
        return accounts;
    }

    @Override
    public Account findByUsername(String username) {
        Account account = accountRepository.findByUsername(username);
        return account;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Account createNewAccount(Account account) {

        Role role = roleService.findByCode("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        // Validate the password
        if (account.getPassword().length() < 8){
            throw new EntityExistsException("password should be greater than 8 characters");
        }

        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));

        account.setRoles(roles);
        
        return accountRepository.save(account);
    
    }
    
}
