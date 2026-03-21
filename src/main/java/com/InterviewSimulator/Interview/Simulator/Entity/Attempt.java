package com.InterviewSimulator.Interview.Simulator.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Test test;

    private Integer total_score;
    private Integer coding_score;
    private Integer mcq_score;
    private Integer theory_score;

    private LocalDateTime startedAt;
    private LocalDateTime submittedAt;
}
