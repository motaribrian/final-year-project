package com.codewithmotari.collegetimetabling.rest.newui;

import com.codewithmotari.collegetimetabling.domain.TimeTable;
import com.codewithmotari.collegetimetabling.persistence.TimeTableRepository;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class TimeTableNewUiController {
    @Autowired
    private TimeTableRepository timeTableRepository;
    @Autowired
    private SolverManager<TimeTable, Long> solverManager;
    @Autowired
    private ScoreManager<TimeTable, HardSoftScore> scoreManager;
    @GetMapping("/")
    public String getadminDashboard(Principal principal, Model model){
        SolverStatus solverStatus = getSolverStatus();
        TimeTable solution = timeTableRepository.findById(TimeTableRepository.SINGLETON_TIME_TABLE_ID);
        scoreManager.updateScore(solution); // Sets the score
        solution.setSolverStatus(solverStatus);


        model.addAttribute("timetable", solution);

        return "admin-dashboard";
    }
    public SolverStatus getSolverStatus() {
        return solverManager.getSolverStatus(TimeTableRepository.SINGLETON_TIME_TABLE_ID);
    }
    @GetMapping("/newui/solverstatus")
    @ResponseBody
    public String getSolverStatus(Principal principal){
        return solverManager.getSolverStatus(TimeTableRepository.SINGLETON_TIME_TABLE_ID).name();
    }

}
