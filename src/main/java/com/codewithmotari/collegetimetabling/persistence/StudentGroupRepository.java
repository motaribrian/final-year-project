package com.codewithmotari.collegetimetabling.persistence;

import com.codewithmotari.collegetimetabling.domain.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;



public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long> {
    StudentGroup findByClassCode(String classCode);
}
