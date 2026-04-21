package com.codewithmotari.collegetimetabling.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Embeddable
public class UserAccount {
    @Id
    @GeneratedValue
    private Long id;
    @Email
    @NotEmpty
    @Column(unique = true, nullable = false,updatable = false)
    private String userName;
    private String password;

}
