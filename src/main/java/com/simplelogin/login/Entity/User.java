package com.simplelogin.login.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String username;
    private String password;

    public User setUsername(String username){
        this.username = username;
        return this;
    }

    public String getUsername(){
        return username;
    }

    public User setPassword(String password){
        this.password = password;
        return this;
    }
}
