package com.codewithmotari.collegetimetabling.persistence;

import com.codewithmotari.collegetimetabling.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
}
