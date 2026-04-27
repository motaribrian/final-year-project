package com.codewithmotari.collegetimetabling.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Teacher implements HasUserAccount{
    @Id
    @GeneratedValue
    private Long id;
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

    public Teacher(String firstNAme, String lastName, UserAccount userAccount) {
        this.firstNAme = firstNAme;
        this.lastName = lastName;
        this.userAccount = userAccount;
    }

    public Teacher() {
    }
}
