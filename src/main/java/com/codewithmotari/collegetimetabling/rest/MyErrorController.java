package com.codewithmotari.collegetimetabling.rest;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MyErrorController {
    @ExceptionHandler({ Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleErrors(Model model,Exception ex){
        model.addAttribute("errorMessage", ex.getMessage());
        return "500Error";
    }
}
