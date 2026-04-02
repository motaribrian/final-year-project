/*
 * Copyright(c) 2021, Developer : Amit Maurya
 */

package com.codewithmotari.collegetimetabling.persistence;

import java.util.List;

import com.codewithmotari.collegetimetabling.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Override
    List<Room> findAll();

}
