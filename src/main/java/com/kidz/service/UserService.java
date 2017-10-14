package com.kidz.service;


import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kidz.login.model.Account;

public interface UserService {

    Page<Account> findAll(Pageable pageable,Map<String, Object> filterMap);

    Account findByUsername(String userename);

    Account createNewAccount(Account user);


}
