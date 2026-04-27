package com.codewithmotari.collegetimetabling.service;

import com.codewithmotari.collegetimetabling.domain.Lesson;
import com.codewithmotari.collegetimetabling.domain.Teacher;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TimetableReportService {

    // 1. Full Master Timetable
    public byte[] exportMasterTimetable(List<Lesson> allLessons) throws JRException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Technical University of Mombasa - Master Timetable");
        return generatePdf("group-timetable.jrxml", allLessons, parameters);
    }

    // 2. Single User (Teacher) Timetable
    public byte[] exportTeacherTimetable(Teacher teacher, List<Lesson> teacherLessons) throws JRException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Lecturer Timetable: " + teacher.getFirstNAme() + " " + teacher.getLastName());
        return generatePdf("teacher-timetable.jrxml", teacherLessons, parameters);
    }

    // 3. Single User Group (Student Group) Timetable
    public byte[] exportStudentGroupTimetable(String studentGroup, List<Lesson> groupLessons) throws JRException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Class Timetable: " + studentGroup);
        return generatePdf("group-timetable.jrxml", groupLessons, parameters);
    }

    private byte[] generatePdf(String templateName, List<Lesson> lessons, Map<String, Object> parameters) throws JRException {
        InputStream reportStream = getClass().getResourceAsStream("/reports/" + templateName);
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        lessons.sort(
                Comparator.comparing(
                        (Lesson l) -> l.getTimeslot() == null ? null : l.getTimeslot().getDayOfWeek(),
                        Comparator.nullsLast(Comparator.naturalOrder())
                ).thenComparing(
                        l -> l.getTimeslot() == null ? null : l.getTimeslot().getStartTime(),
                        Comparator.nullsLast(Comparator.naturalOrder())
                )
        );

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lessons);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}