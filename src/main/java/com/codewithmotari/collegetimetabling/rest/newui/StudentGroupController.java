package com.codewithmotari.collegetimetabling.rest.newui;

import com.codewithmotari.collegetimetabling.domain.StudentGroup;
import com.codewithmotari.collegetimetabling.persistence.ProgramRepository;
import com.codewithmotari.collegetimetabling.persistence.StudentGroupRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/classes")
public class StudentGroupController {
    @Autowired
    private StudentGroupRepository studentGroupRepository;
    @Autowired private ProgramRepository programRepository;
    @GetMapping("")
    public String getAllClasses(Model model) {
        model.addAttribute("classes", studentGroupRepository.findAll());
        return "all-classes";
    }

    @GetMapping("/add")
    public String addClassForm(Model model) {
        model.addAttribute("programs", programRepository.findAll());
        model.addAttribute("class", new StudentGroup());
        return "add-class";
    }
    @PostMapping("/add")
    public String addClass( @Valid @ModelAttribute StudentGroup sg,BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error ->{
                    System.out.println(error.getDefaultMessage());

            });
            model.addAttribute("programs", programRepository.findAll());
            model.addAttribute("class", sg);
            return "add-class";
        }

        try{
            sg=studentGroupRepository.save(sg);
            return "redirect:/classes/"+sg.getClassCode();
        }catch(Exception e){
            return "redirect:/classes";
        }


    }
    @GetMapping("/{classCode}")
    public String getSingleClass(@PathVariable ("classCode")String classCode, Model model) {
            try {
                StudentGroup sg = studentGroupRepository.findByClassCode(classCode);
                if(sg==null){
                    System.out.println("couldnt find class with code "+classCode);
                    throw new IllegalArgumentException("Make sure To provide a valid class code");
                }
                model.addAttribute("programs", programRepository.findAll());
                model.addAttribute("class",sg);
                System.out.println(sg);
            }catch (Exception e){
                System.out.println("an error has occured" + e);
                return "redirect:/";
            }

        return "single-class";
    }
}
