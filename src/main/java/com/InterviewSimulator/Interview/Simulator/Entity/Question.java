package com.InterviewSimulator.Interview.Simulator.Entity;

import com.InterviewSimulator.Interview.Simulator.Enumerations.QuestionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200000)
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(length = 200000)
    private QuestionType type;

    @Column(length = 200000)
    private String description;

    @OneToMany(mappedBy ="question",cascade= CascadeType.ALL)
    private List<Test_cases> testcases;


    @ManyToOne
    @JoinColumn(name = "section_id")
    private Sections section;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Options> options;

}
