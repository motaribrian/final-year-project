package com.codewithmotari.collegetimetabling.dto;

public class LessonReportDto {
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String subject;
    private String studentGroup;
    private String roomName;

    public LessonReportDto(String dayOfWeek, String startTime, String endTime,
                           String subject, String studentGroup, String roomName) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
        this.studentGroup = studentGroup;
        this.roomName = roomName;
    }

    // Include Getters for all fields (Required by JasperReports)
    public String getDayOfWeek() { return dayOfWeek; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getSubject() { return subject; }
    public String getStudentGroup() { return studentGroup; }
    public String getRoomName() { return roomName; }
}