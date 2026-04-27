package com.codewithmotari.collegetimetabling.rest.newui;

import com.codewithmotari.collegetimetabling.domain.Lesson;
import com.codewithmotari.collegetimetabling.domain.Teacher;
import com.codewithmotari.collegetimetabling.persistence.LessonRepository;
import com.codewithmotari.collegetimetabling.persistence.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LecturerController {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @GetMapping("/lecturers/{lecId}/page")
    public String getLecturerPage(@PathVariable("lecId") Long lecId, Model model) {
        Teacher teacher=teacherRepository.findById(lecId).orElse(null);
        List<Lesson> lessonList=new ArrayList<>();
        if(teacher!=null){
            lessonList=lessonRepository.findByTeacher(teacher);
        }

        model.addAttribute("lessonList",lessonList);
        model.addAttribute("teacher",teacher);

        return "lecturer";
    }
}
