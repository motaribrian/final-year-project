package com.codewithmotari.collegetimetabling.persistence;

import com.codewithmotari.collegetimetabling.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserAccountRepository extends JpaRepository <UserAccount, Long> {

    UserDetails findByUserName(String s);
}
