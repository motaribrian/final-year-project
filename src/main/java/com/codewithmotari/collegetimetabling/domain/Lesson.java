
/*
 * Copyright(c) 2021, Developer : Amit Maurya
 */

package com.codewithmotari.collegetimetabling.domain;

import jakarta.persistence.*;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
@Entity
public class Lesson {

    @PlanningId
    @Id @GeneratedValue
    private Long id;

    private String subject;
    @ManyToOne()
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    private String studentGroup;

    @PlanningVariable(valueRangeProviderRefs = "timeslotRange")
    @ManyToOne
    private Timeslot timeSlot;

    @PlanningVariable(valueRangeProviderRefs = "roomRange")
    @ManyToOne
    private Room room;

    // No-arg constructor required for Hibernate and OptaPlanner
    public Lesson() {
    }

    public Lesson(String subject, Teacher teacher, String studentGroup) {
        this.subject = subject.trim();
        this.teacher = teacher;
        this.studentGroup = studentGroup.trim();
    }

    public Lesson(long id, String subject, Teacher teacher, String studentGroup, Timeslot timeslot, Room room) {
        this(subject, teacher, studentGroup);
        this.id = id;
        this.timeSlot = timeslot;
        this.room = room;
    }

    @Override
    public String toString() {
        return subject + "(" + id + ")";
    }

    // ************************************************************************
    // Getters and setters
    // ************************************************************************

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getSubject() {
        return subject;
    }

    public Timeslot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(Timeslot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getStudentGroup() {
        return studentGroup;
    }



    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeSlot = timeslot;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}
