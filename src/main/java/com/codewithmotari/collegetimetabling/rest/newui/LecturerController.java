package com.codewithmotari.collegetimetabling.rest.newui;

import com.codewithmotari.collegetimetabling.domain.Lesson;
import com.codewithmotari.collegetimetabling.domain.Teacher;
import com.codewithmotari.collegetimetabling.domain.TimeTable;
import com.codewithmotari.collegetimetabling.persistence.LessonRepository;
import com.codewithmotari.collegetimetabling.persistence.TeacherRepository;
import com.codewithmotari.collegetimetabling.persistence.TimeTableRepository;
import com.codewithmotari.collegetimetabling.persistence.TimeslotRepository;
import com.codewithmotari.collegetimetabling.service.TimetableReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class LecturerController {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private TimeslotRepository timeslotRepository;

    @Autowired
    private TimetableReportService reportService;

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
        Teacher teacher=teacherRepository.findById(lecId).orElse(null);
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
}
