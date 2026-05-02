package com.codewithmotari.collegetimetabling.domain;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class StudentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentGroupId;
    @OneToMany(mappedBy = "studentGroup")
    private List<Lesson> lessons=new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;
    @Column(nullable = false)
    private Year year;
    @Column(name = "classcode", nullable = false, length = 10,unique = true)
    private String classCode;

    public StudentGroup(Program program) {
        this.program=program;
        this.year=Year.now();
    }

    public StudentGroup(Program program,Year year) {
        this.program=program;
        this.year=year;
    }

    @PrePersist
    private void setClassCodebeforeSave(){
        setClassCode(this.program.getProgrammeCode()+this.getYear().toString());
    }

    @Override
    public String toString() {
        return "StudentGroup{" +
                "studentGroupId=" + studentGroupId +
//                ", program=" + program +
                ", year=" + year +
                ", classCode='" + classCode + '\'' +
                '}';
    }
}
