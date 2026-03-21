package com.InterviewSimulator.Interview.Simulator.DTO.Test.TestResponse;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDto {

    private String question;
    private long questionId;
    private String title;
    private String description;
    private List<TestCasesResponseDto> testcases;
    private List<String> options;
}
