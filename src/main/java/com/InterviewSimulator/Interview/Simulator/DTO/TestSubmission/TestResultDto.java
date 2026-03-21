package com.InterviewSimulator.Interview.Simulator.DTO.TestSubmission;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestResultDto {

    private long coding_score;
    private long mcq_score;
    private long theory_score;
    private long total_score;
}
