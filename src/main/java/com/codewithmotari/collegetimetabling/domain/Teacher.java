package com.codewithmotari.collegetimetabling.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Teacher implements HasUserAccount{
    @Id
    @GeneratedValue
    private String firstNAme;
    private String lastName;
    @Embedded
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
