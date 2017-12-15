package com.kidz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kidz.login.model.Account;
import com.kidz.login.model.Role;
import com.kidz.repository.UserRepository;
import javax.persistence.EntityExistsException;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository accountRepository;

    @Autowired
    private RoleService roleService;

 

    @Override
    public Account findByUsername(String username) {
        Account account = accountRepository.findByUsername(username);
        return account;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Account createNewAccount(Account account) {

        boolean hasRole=false;
        
        for (Role role1 : account.getRoles()) 	
        	if(role1.getCode().equals("ROLE_USER"))
        		hasRole=true;
        
        
        if(!hasRole){
            Role role = roleService.findByCode("ROLE_USER");
            account.getRoles().add(role);
        }
        
        account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
        
        return accountRepository.save(account);
    
    }

	@Override
	public Page<Account> findAll(Pageable pageable, Map<String, Object> filterMap) {
		return accountRepository.getAllUsers(pageable);
	}
    
}
