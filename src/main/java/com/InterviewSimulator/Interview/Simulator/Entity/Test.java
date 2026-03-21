package com.InterviewSimulator.Interview.Simulator.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length= 20000)
    private String test_title;

    private Integer duration;

    private LocalDate  date;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy ="test", cascade = CascadeType.ALL)
    private List<Sections> sections;

    @OneToMany(mappedBy ="test", cascade = CascadeType.ALL)
    private List<Question> questions;
}
