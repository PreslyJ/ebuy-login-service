package com.kidz.service;

import com.kidz.model.Role;

public interface RoleService {

    Role findById(Long id);

    Role findByCode(String code);

}
