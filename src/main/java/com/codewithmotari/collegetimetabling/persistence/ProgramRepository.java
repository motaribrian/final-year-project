package com.codewithmotari.collegetimetabling.persistence;

import com.codewithmotari.collegetimetabling.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}
