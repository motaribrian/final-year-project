package com.codewithmotari.collegetimetabling.config;

import com.codewithmotari.collegetimetabling.domain.*;
import com.codewithmotari.collegetimetabling.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Year;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserAccountRepository userAccountRepository;
    private final TeacherRepository teacherRepository;
    private final RoomRepository roomRepository;
    private final TimeslotRepository TimeslotRepository;
    private final LessonRepository lessonRepository;
    @Autowired private StudentGroupRepository studentGroupRepository;
    @Autowired private ProgramRepository programRepository;
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        if (userAccountRepository.count() > 0) return;


        // ==========================================
        // 1. USER ACCOUNTS
        // ==========================================
        UserAccount u1 = createUser("user@user.com");

        UserAccount u2 = createUser("mary.jane@gmail.com");
        UserAccount u3 = createUser("alex.kim@gmail.com");
        UserAccount u4 = createUser("sarah.ali@gmail.com");
        UserAccount u5 = createUser("daniel.oko@gmail.com");

        userAccountRepository.saveAll(List.of(u1, u2, u3, u4, u5));

        // ==========================================
        // 2. TEACHERS
        // ==========================================
        Teacher t1 = new Teacher("John", "Omondi", u1);
        Teacher t2 = new Teacher("Sarah", "Mutuku", u2);
        Teacher t3 = new Teacher("Peter", "Njeri", u3);
        Teacher t4 = new Teacher("Alice", "Wamalwa", u4);

        teacherRepository.saveAll(List.of(t1, t2, t3, t4));

        // ==========================================
        // 3. ROOMS
        // ==========================================
        Room r1 = new Room("Engineering Block - Room E101");
        Room r2 = new Room("Engineering Block - Room E102");
        Room r3 = new Room("Main Computer Lab - Library Wing");
        Room r4 = new Room("Business Block - Hall B1");
        Room r5 = new Room("Applied Sciences - Lab 3");

        roomRepository.saveAll(List.of(r1, r2, r3, r4, r5));

        // ==========================================
        // 4. TimeslotS
        // ==========================================
        Timeslot ts1 = new Timeslot(DayOfWeek.of(1), LocalTime.of(8, 0), LocalTime.of(10, 0));
        Timeslot ts2 = new Timeslot(DayOfWeek.of(1), LocalTime.of(10, 30), LocalTime.of(12, 30));
        Timeslot ts3 = new Timeslot(DayOfWeek.of(1), LocalTime.of(14, 0), LocalTime.of(16, 0));

        Timeslot ts4 = new Timeslot(DayOfWeek.of(2), LocalTime.of(8, 0), LocalTime.of(10, 0));
        Timeslot ts5 = new Timeslot(DayOfWeek.of(2), LocalTime.of(10, 30), LocalTime.of(12, 30));
        Timeslot ts6 = new Timeslot(DayOfWeek.of(2), LocalTime.of(14, 0), LocalTime.of(16, 0));

        Timeslot ts7 = new Timeslot(DayOfWeek.of(3), LocalTime.of(8, 0), LocalTime.of(10, 0));
        Timeslot ts8 = new Timeslot(DayOfWeek.of(3), LocalTime.of(10, 30), LocalTime.of(12, 30));

        TimeslotRepository.saveAll(List.of(ts1, ts2, ts3, ts4, ts5, ts6, ts7, ts8));


        var compScience=new Program();
        compScience.setProgrammeName("Bachelor of Science in Computer Science");
        compScience.setProgrammeCode("BSCS");

        compScience=programRepository.save(compScience);


//        =================================================
        var stg1=new  StudentGroup(compScience, Year.now());
        var stg2=new  StudentGroup(compScience, Year.now().minusYears(4));
        var stg3=new  StudentGroup(compScience, Year.now().minusYears(3));
        var stg5=new  StudentGroup(compScience, Year.now().minusYears(2));
        var stg6=new  StudentGroup(compScience, Year.now().minusYears(1));


        studentGroupRepository.saveAll(List.of(stg1,stg2,stg3,stg5,stg6));


//        ==================================================
        // ==========================================
        // 5. LESSONS (UNASSIGNED - FOR SOLVER)
        // ==========================================
        Lesson l1 = new Lesson("Constraint Programming",  t2,stg1);
        Lesson l2 = new Lesson("Artificial Intelligence", t1,stg2);
        Lesson l3 = new Lesson("Software Architecture", t1, stg3);

        Lesson l5 = new Lesson("Database Management Systems", t4, stg5);
        Lesson l6 = new Lesson("Web Systems Development", t3, stg6);

        lessonRepository.saveAll(List.of(l1, l2, l3, l5, l6));
    }

    private UserAccount createUser(String email) {
        UserAccount u = new UserAccount();
        u.setUserName(email);
        u.setPassword("$2a$10$VxQY.aUhXs4DpOLcbKwdWOzrQjqEjKK1yEYxyc0RY2y1v/ctm9I82");
        u.setAccountNonExpired(true);
        u.setAccountNonLocked(true);
        u.setCredentialsNonExpired(true);
        u.setEnabled(true);
        return u;
    }
}
