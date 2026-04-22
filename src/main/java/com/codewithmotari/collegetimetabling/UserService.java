package com.codewithmotari.collegetimetabling;

import ch.qos.logback.core.encoder.Encoder;
import com.codewithmotari.collegetimetabling.domain.UserAccount;
import com.codewithmotari.collegetimetabling.persistence.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userAccountRepository.findByUserName(s);

    }


    public UserAccount createNewUser(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }
}
