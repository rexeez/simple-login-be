package com.simplelogin.login.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.simplelogin.login.Entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
    
    public Optional<User> findByUsername(String username);

}
