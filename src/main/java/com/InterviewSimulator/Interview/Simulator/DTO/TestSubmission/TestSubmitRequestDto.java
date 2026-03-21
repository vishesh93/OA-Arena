package com.InterviewSimulator.Interview.Simulator.DTO.TestSubmission;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestSubmitRequestDto {

    private long testId;
    private List<AnswerDto> answer;
}
