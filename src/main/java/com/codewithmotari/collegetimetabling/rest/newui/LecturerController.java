package com.codewithmotari.collegetimetabling.rest.newui;

import com.codewithmotari.collegetimetabling.UserService;
import com.codewithmotari.collegetimetabling.domain.*;
import com.codewithmotari.collegetimetabling.dto.Registerdto;
import com.codewithmotari.collegetimetabling.persistence.LessonRepository;
import com.codewithmotari.collegetimetabling.persistence.TeacherRepository;
import com.codewithmotari.collegetimetabling.persistence.TimeTableRepository;
import com.codewithmotari.collegetimetabling.persistence.TimeslotRepository;
import com.codewithmotari.collegetimetabling.service.TimetableReportService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class LecturerController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private TimeslotRepository timeslotRepository;

    @Autowired
    private TimetableReportService reportService;
    @Autowired
    private UserService userService;

    // Assuming you have a service to fetch the current solved timetable
    @Autowired
    private TimeTableRepository timeTableService;

    @GetMapping("/teacher/{id}/pdf")
    @ResponseBody
    public byte[] downloadTeacherTimetable(@PathVariable("id") Long id) {
        try {
            Teacher teacher=teacherRepository.findById(id).get();

            if(teacher!=null){
                log.debug("getting list of subjects for teacher {}",teacher.getFirstNAme());
            List<Lesson> lessonlist=lessonRepository.findByTeacher(teacher);
            log.debug("found {} lessons",lessonlist.size());
            byte[] body =reportService.exportTeacherTimetable(teacher,lessonlist);
            return body;
            }
            log.debug("teacher is null wont poceed with the request");
            throw new RuntimeException("An error Occured");

        } catch (Exception e) {
            return null;
        }
    }


    @GetMapping("/lecturers/{lecId}/page")
    public String getLecturerPage(@PathVariable("lecId") Long lecId, Model model) {
        Teacher teacher=teacherRepository.findById(lecId).orElseThrow(()->new RuntimeException("Lecturer not found"));
        List<Lesson> lessonList=new ArrayList<>();
        if(teacher!=null){
            lessonList=lessonRepository.findByTeacher(teacher);
        }

        model.addAttribute("days", DayOfWeek.values());
        model.addAttribute("timeslots",timeslotRepository.findAll());
        model.addAttribute("lessonList",lessonList);
        model.addAttribute("teacher",teacher);

        return "lecturer";
    }

    @GetMapping("/lecturers")
    public String getLecturersPage(Model model) {
        model.addAttribute("teachers",teacherRepository.findAll());
        return "lecturers";
    }

    @GetMapping("/lecturers/save")
    public String saveNewLecturer(Model model){
        model.addAttribute("lecturer",new Registerdto());
        return "add-lecturer";
    }
    @PostMapping("/lecturers/save")
    public String register(@Valid @ModelAttribute Registerdto registerdto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            log.warn(bindingResult.getFieldError().getDefaultMessage());
            model.addAttribute("authdto",registerdto);
            return "add-lecturer";
        }
        try{
            UserAccount userAccount=new UserAccount();
            userAccount.setUserName(registerdto.getEmail());
            userAccount.setPassword(passwordEncoder.encode(registerdto.getPassword()));
            userAccount.setAccountNonExpired(true);
            userAccount.setAccountNonLocked(true);
            userAccount.setEnabled(true);
            userAccount.setCredentialsNonExpired(true);
            userAccount.setUserRoles(List.of(ROLE.TEACHER));

            UserAccount savedUser=userService.createNewUser(userAccount);
            log.info("User Account Created Successfully");
            log.info("Creatinny role {} ",registerdto.getUserRoles().get(0).name());


                Teacher teacher=new Teacher();
                teacher.setUserAccount(savedUser);
                teacher.setFirstNAme(registerdto.getFirstName());
                teacher.setLastName(registerdto.getLastName());

                Teacher savedTeacher=teacherRepository.save(teacher);

        }catch (DataIntegrityViolationException e){
            bindingResult.addError(new ObjectError("username","UsernameAlreadyTaken"));
            model.addAttribute("authdto",registerdto);
            return "add-lecturer";
        }



        return "redirect:/";

    }


}
