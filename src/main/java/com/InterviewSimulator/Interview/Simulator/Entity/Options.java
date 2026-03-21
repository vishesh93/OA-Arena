package com.InterviewSimulator.Interview.Simulator.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionText;

    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public void setisCorrect(boolean ans){
     this.isCorrect = ans;
}


    public boolean getIsCorrect() {
        return isCorrect;
    }
}