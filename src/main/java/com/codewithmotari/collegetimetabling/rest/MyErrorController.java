package com.codewithmotari.collegetimetabling.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/error")
    public String handleErrors(){
        return "500Error";
    }
}
