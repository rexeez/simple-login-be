package com.simplelogin.login.DTO;

public class UserRegisterDto {
    private String username;
    private String password;

    public UserRegisterDto setUsername(String username){
        this.username = username;
        return this;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
