package com.kidz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kidz.login.model.Account;


@Repository
public interface UserRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.username = :username")
    Account findByUsername(@Param("username") String username);

    @Query("SELECT a FROM Account a inner join a.roles r   WHERE r.code='ROLE_USER'")
    Page<Account> getAllUsers(Pageable pageable);
    
}
