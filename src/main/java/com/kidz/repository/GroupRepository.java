package com.kidz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kidz.login.model.UserGroup;

@Repository
public interface GroupRepository extends JpaRepository<UserGroup, Long>{
   
}
