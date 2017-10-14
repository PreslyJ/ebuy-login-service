package com.kidz.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kidz.login.model.Role;
import com.kidz.repository.RoleRepository;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleServiceBean implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findById(Long id) {
        Role role = roleRepository.findOne(id);
        return role;
    }

    @Override
    public Role findByCode(String code) {
       return roleRepository.findByCode(code);
    }

	@Override
	public Page<Role> findAll(Pageable pageable, Map<String, Object> filterMap) {
		return roleRepository.findAll(pageable);
	}

	@Override
	public void createRole(Role role) {
		roleRepository.save(role);
	}
    
}
