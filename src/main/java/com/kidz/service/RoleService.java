package com.kidz.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kidz.login.model.Role;

public interface RoleService {

    Role findById(Long id);

    Role findByCode(String code);
    
    Page<Role> findAll(Pageable pageable,Map<String, Object> filterMap);
    
    void createRole(Role role);
    
}
