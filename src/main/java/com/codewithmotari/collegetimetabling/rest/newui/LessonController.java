package com.codewithmotari.collegetimetabling.rest.newui;

import com.codewithmotari.collegetimetabling.domain.Lesson;
import com.codewithmotari.collegetimetabling.domain.Teacher;
import com.codewithmotari.collegetimetabling.persistence.LessonRepository;
import com.codewithmotari.collegetimetabling.persistence.RoomRepository;
import com.codewithmotari.collegetimetabling.persistence.TeacherRepository;
import com.codewithmotari.collegetimetabling.persistence.TimeslotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class LessonController {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private TimeslotRepository timeslotRepository;
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/lessons/add")
    public String getLessonAddForm(Model model) {
        List<Teacher> teacherList = teacherRepository.findAll();
        model.addAttribute("lesson", new Lesson());
        model.addAttribute("teachers",teacherList);
        return "add-lesson";
    }
    @PostMapping("/lessons/save")
    public String saveLesson(Model model,@ModelAttribute("lesson") Lesson lesson) {
        try{
            lessonRepository.save(lesson);
            log.debug("lesson saved successfully");
        }catch(Exception e){
            log.error("Error while saving lesson");
            model.addAttribute("error",e.getMessage());
            model.addAttribute("lesson",lesson);
            model.addAttribute("teachers",teacherRepository.findAll());
            return "add-lesson";
        }
        return "redirect:/";
    }


    @GetMapping("/lessons/{id}/edit")
    public String editLecturer(Model model, @PathVariable("id") Long id) {
        try {
            Lesson lesson=lessonRepository.findById(id).get();
            if(lesson!=null){
                model.addAttribute("lesson",lesson);
                model.addAttribute("timeslots",timeslotRepository.findAll());
                model.addAttribute("teachers",teacherRepository.findAll());
                model.addAttribute("rooms",roomRepository.findAll());
                return "edit-lesson";
            }return  "redirect:/";
        }catch(Exception e){

            return "redirect:/";
        }

    }


}
