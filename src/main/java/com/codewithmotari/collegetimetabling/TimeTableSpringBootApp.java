

/*
 * Copyright(c) 2021, Developer : Amit Maurya
 */

package com.codewithmotari.collegetimetabling;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import com.codewithmotari.collegetimetabling.domain.*;
import com.codewithmotari.collegetimetabling.persistence.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;

import com.codewithmotari.collegetimetabling.persistence.LessonRepository;
import com.codewithmotari.collegetimetabling.persistence.RoomRepository;
import com.codewithmotari.collegetimetabling.persistence.TimeslotRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TimeTableSpringBootApp {

    public static void main(String[] args) {
        SpringApplication.run(TimeTableSpringBootApp.class, args);
    }


    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



}
