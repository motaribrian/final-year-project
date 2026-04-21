package com.codewithmotari.collegetimetabling.dto;

public class AuthDto {
    private String email;
    private String password;

    public AuthDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
