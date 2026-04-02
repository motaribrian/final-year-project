/*
 * Copyright(c) 2021, Developer : Amit Maurya
 */

package com.codewithmotari.collegetimetabling.persistence;

import java.util.List;

import com.codewithmotari.collegetimetabling.domain.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {

    @Override
    List<Timeslot> findAll();

}
