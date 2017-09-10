package com.kidz.service;


import java.util.Collection;

import com.kidz.model.Account;

public interface UserService {

    Collection<Account> findAll();

    Account findByUsername(String userename);

    Account createNewAccount(Account user);


}
