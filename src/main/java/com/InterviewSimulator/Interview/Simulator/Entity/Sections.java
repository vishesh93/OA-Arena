package com.InterviewSimulator.Interview.Simulator.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String section_title;
    private String section_type;
    private String instructions;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

}
