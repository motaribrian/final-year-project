package com.codewithmotari.collegetimetabling.service;

import com.codewithmotari.collegetimetabling.domain.Lesson;
import com.codewithmotari.collegetimetabling.domain.Teacher;
import com.codewithmotari.collegetimetabling.domain.TimeTable;
import com.codewithmotari.collegetimetabling.dto.LessonReportDto;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
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
        log.debug("calling generatepdf");
        return generatePdf("lecturer-timetable.jrxml", teacherLessons, parameters);
    }

    // 3. Single User Group (Student Group) Timetable
    public byte[] exportStudentGroupTimetable(String studentGroup, List<Lesson> groupLessons) throws JRException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Class Timetable: " + studentGroup);
        return generatePdf("group-timetable.jrxml", groupLessons, parameters);
    }

    private byte[] generatePdf(String templateName, List<Lesson> lessons, Map<String, Object> parameters) throws JRException {
        InputStream reportStream = getClass().getResourceAsStream("/reports/" + templateName);
        log.debug("created inputstream from template file");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
        log.debug("created jasper report from inputscream : {}",jasperReport.getName());

        lessons.sort(
                Comparator.comparing(
                        (Lesson l) -> l.getTimeSlot() == null ? null : l.getTimeSlot().getDayOfWeek(),
                        Comparator.nullsLast(Comparator.naturalOrder())
                ).thenComparing(
                        l -> l.getTimeSlot() == null ? null : l.getTimeSlot().getStartTime(),
                        Comparator.nullsLast(Comparator.naturalOrder())
                )
        );
        log.debug("sorted list of {} lessons to pass as datasource ",lessons.size());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lessons);
        try {
            log.debug("Attempting to fill report with {} lessons", lessons.size());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            log.debug("Report filled successfully");
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Throwable t) {
            log.error("CRITICAL ERROR during Jasper fill: ", t);
            throw new RuntimeException("Jasper failed to fill", t);
        }


    }


    public byte[] generateTeacherTimetablePdf(TimeTable solvedTimeTable, Long teacherId, String teacherFullName) throws JRException {

        // 1. Filter, Sort, and Map to DTO
        List<LessonReportDto> reportData = solvedTimeTable.getLessonList().stream()
                // Only lessons for this teacher
                .filter(lesson -> lesson.getTeacher() != null && lesson.getTeacher().getId().equals(teacherId))
                // Only scheduled lessons (ignore unassigned)
                .filter(lesson -> lesson.getTimeSlot() != null && lesson.getRoom() != null)
                // Sort by Day, then by Start Time
                .sorted(Comparator.comparing((Lesson l) -> l.getTimeSlot().getDayOfWeek())
                        .thenComparing(l -> l.getTimeSlot().getStartTime()))
                // Map to flat DTO
                .map(lesson -> new LessonReportDto(
                        lesson.getTimeSlot().getDayOfWeek().toString(),
                        lesson.getTimeSlot().getStartTime().toString(),
                        lesson.getTimeSlot().getEndTime().toString(),
                        lesson.getSubject(),
                        lesson.getStudentGroup().getClassCode(),
                        lesson.getRoom().getName()
                ))
                .collect(Collectors.toList());

        // 2. Load the JRXML template from src/main/resources
        InputStream templateStream = getClass().getResourceAsStream("/reports/teacher_timetable.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

        // 3. Create the Data Source
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData);

        // 4. Set Parameters (e.g., the Teacher's Name for the report title)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("teacherName", teacherFullName);

        // 5. Fill and Export
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}