package com.codewithmotari.collegetimetabling.rest;

import com.codewithmotari.collegetimetabling.dto.AuthDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("authdto",new AuthDto());
        return "login";
    }
    @GetMapping("/sign-up")
    public String signUpPage(Model model) {
        model.addAttribute("authdto",new AuthDto());
        return "signup";
    }
}
