package com.InterviewSimulator.Interview.Simulator.DTO.TestSubmission;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class AnswerDto {

    @JsonProperty("question_id")
    private Long question_id;

    private String code;
    private String answer;
    private String option;
}
