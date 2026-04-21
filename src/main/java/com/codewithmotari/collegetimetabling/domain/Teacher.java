package com.codewithmotari.collegetimetabling.domain;

import jakarta.persistence.*;

@Entity
public class Teacher implements HasUserAccount{
    @Id
    @GeneratedValue
    private String firstNAme;
    private String lastName;
    @OneToOne(cascade = CascadeType.ALL)
    private UserAccount  userAccount;

    @Override
    public UserAccount getUserAccount() {
        return userAccount;
    }

    @Override
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
