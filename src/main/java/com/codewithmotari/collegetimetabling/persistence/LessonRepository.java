/*
 * Copyright(c) 2021, Developer : Amit Maurya
 */

package com.codewithmotari.collegetimetabling.persistence;

import java.util.List;

import com.codewithmotari.collegetimetabling.domain.Lesson;
import com.codewithmotari.collegetimetabling.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Override
    List<Lesson> findAll();

    List<Lesson> findByTeacher(Teacher teacher);
}
