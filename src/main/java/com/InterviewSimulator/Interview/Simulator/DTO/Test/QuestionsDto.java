package com.InterviewSimulator.Interview.Simulator.DTO.Test;


import com.InterviewSimulator.Interview.Simulator.Entity.Test_cases;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class QuestionsDto {


    private String question;
    private List<String> options;
    private String correct_answer;

    private List<Test_casesDto> test_cases;

    private String title;
    private String description;
}