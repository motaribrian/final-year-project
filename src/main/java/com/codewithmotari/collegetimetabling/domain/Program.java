package com.codewithmotari.collegetimetabling.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String programmeName;
    @Column(length = 5, nullable = false,unique = true)
    private String programmeCode;
    @OneToMany(mappedBy = "program")
    private List<StudentGroup> studentGroups=new ArrayList<>();

}
