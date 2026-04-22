package com.codewithmotari.collegetimetabling.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticController  {
    @GetMapping("/")
    public String home(){
        return "index";
    }



}
