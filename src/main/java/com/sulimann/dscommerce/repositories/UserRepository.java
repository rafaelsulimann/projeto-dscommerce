package com.sulimann.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sulimann.dscommerce.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByEmail(String email);
}
