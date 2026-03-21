package com.InterviewSimulator.Interview.Simulator.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Test_cases {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String input;
    private String Expectedoutput;

    @Column(nullable = true)
    public Boolean isHidden = false;

    @ManyToOne
    @JoinColumn(name = "Question_id",nullable = false)
    private Question question;

    public boolean isHidden() {
        return isHidden;
    }
}
