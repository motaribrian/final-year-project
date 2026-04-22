package com.codewithmotari.collegetimetabling.rest;

import com.codewithmotari.collegetimetabling.UserService;
import com.codewithmotari.collegetimetabling.domain.ROLE;
import com.codewithmotari.collegetimetabling.domain.Teacher;
import com.codewithmotari.collegetimetabling.domain.UserAccount;
import com.codewithmotari.collegetimetabling.dto.AuthDto;
import com.codewithmotari.collegetimetabling.dto.Registerdto;
import com.codewithmotari.collegetimetabling.persistence.TeacherRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherRepository teacherRepository;
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("authdto",new Registerdto());
        return "login";
    }
    @GetMapping("/sign-up")
    public String signUpPage(Model model) {
        model.addAttribute("authdto",new Registerdto());
        return "signup";
    }
    @PostMapping("/sign-up")
    public String register(@Valid @ModelAttribute Registerdto registerdto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            log.warn(bindingResult.getFieldError().getDefaultMessage());
            model.addAttribute("authdto",registerdto);
            return "redirect:/auth/sign-up";
        }
        try{
        UserAccount userAccount=new UserAccount();
        userAccount.setUserName(registerdto.getEmail());
        userAccount.setPassword(passwordEncoder.encode(registerdto.getPassword()));
        userAccount.setAccountNonExpired(true);
        userAccount.setAccountNonLocked(true);
        userAccount.setEnabled(true);
        userAccount.setCredentialsNonExpired(true);
        userAccount.setUserRoles(registerdto.getUserRoles());

        UserAccount savedUser=userService.createNewUser(userAccount);

        Teacher teacher=new Teacher();
        teacher.setUserAccount(savedUser);
        teacher.setFirstNAme(registerdto.getFirstName());
        teacher.setLastName(registerdto.getLastName());

        Teacher savedTeacher=teacherRepository.save(teacher);
        }catch (DataIntegrityViolationException e){
            bindingResult.addError(new ObjectError("username","UsernameAlreadyTaken"));
            model.addAttribute("authdto",registerdto);
            signUpPage(model);
        }



        return "redirect:/";

    }
}
