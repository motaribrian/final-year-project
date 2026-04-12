package com.codewithmotari.collegetimetabling.rest;

import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.codewithmotari.collegetimetabling.domain.TimeTable;
import com.codewithmotari.collegetimetabling.domain.Lesson;
import com.codewithmotari.collegetimetabling.domain.Room;
import com.codewithmotari.collegetimetabling.domain.Timeslot;
import com.codewithmotari.collegetimetabling.persistence.TimeTableRepository;
import com.codewithmotari.collegetimetabling.persistence.LessonRepository;
import com.codewithmotari.collegetimetabling.persistence.RoomRepository;
import com.codewithmotari.collegetimetabling.persistence.TimeslotRepository;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TimeTableController {

    @Autowired
    private TimeTableRepository timeTableRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private TimeslotRepository timeslotRepository;

    @Autowired
    private SolverManager<TimeTable, Long> solverManager;
    @Autowired
    private ScoreManager<TimeTable, HardSoftScore> scoreManager;

    // Tango colors for the frontend
    private static final String[] COLORS = {
            "#8ae234", "#fce94f", "#729fcf", "#e9b96e", "#ad7fa8",
            "#73d216", "#edd400", "#3465a4", "#c17d11", "#75507b"
    };

    // ==========================================
    // CORE TIMETABLE & SOLVER ENDPOINTS
    // ==========================================

    @GetMapping("/")
    public String index(Model model) {
        SolverStatus solverStatus = getSolverStatus();
        TimeTable solution = timeTableRepository.findById(TimeTableRepository.SINGLETON_TIME_TABLE_ID);
        scoreManager.updateScore(solution);
        solution.setSolverStatus(solverStatus);

        // Generate dynamic colors for subjects based on the server-side logic
        Map<String, String> subjectColors = new HashMap<>();
        int colorIndex = 0;
        if (solution.getLessonList() != null) {
            for (Lesson lesson : solution.getLessonList()) {
                if (!subjectColors.containsKey(lesson.getSubject())) {
                    subjectColors.put(lesson.getSubject(), COLORS[colorIndex % COLORS.length]);
                    colorIndex++;
                }
            }
        }

        model.addAttribute("timeTable", solution);
        model.addAttribute("subjectColors", subjectColors);
        model.addAttribute("isSolving", solverStatus != SolverStatus.NOT_SOLVING);

        return "index";
    }

    public SolverStatus getSolverStatus() {
        return solverManager.getSolverStatus(TimeTableRepository.SINGLETON_TIME_TABLE_ID);
    }

    @PostMapping("/solve")
    public String solve() {
        solverManager.solveAndListen(TimeTableRepository.SINGLETON_TIME_TABLE_ID,
                timeTableRepository::findById,
                timeTableRepository::save);
        return "redirect:/";
    }

    @PostMapping("/stopSolving")
    public String stopSolving() {
        solverManager.terminateEarly(TimeTableRepository.SINGLETON_TIME_TABLE_ID);
        return "redirect:/";
    }

    // ==========================================
    // CRUD ENDPOINTS FOR HTML FORMS
    // ==========================================

    // --- LESSONS ---
    @PostMapping("/lessons")
    public String addLesson(@ModelAttribute Lesson lesson) {
        lessonRepository.save(lesson);
        return "redirect:/";
    }

    @PostMapping("/lessons/delete/{id}")
    public String deleteLesson(@PathVariable Long id) {
        lessonRepository.deleteById(id);
        return "redirect:/";
    }

    // --- ROOMS ---
    @PostMapping("/rooms")
    public String addRoom(@ModelAttribute Room room) {
        roomRepository.save(room);
        return "redirect:/";
    }

    @PostMapping("/rooms/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomRepository.deleteById(id);
        return "redirect:/";
    }

    // --- TIMESLOTS ---
    @PostMapping("/timeslots")
    public String addTimeslot(@ModelAttribute Timeslot timeslot) {
        timeslotRepository.save(timeslot);
        return "redirect:/";
    }

    @PostMapping("/timeslots/delete/{id}")
    public String deleteTimeslot(@PathVariable Long id) {
        timeslotRepository.deleteById(id);
        return "redirect:/";
    }
}