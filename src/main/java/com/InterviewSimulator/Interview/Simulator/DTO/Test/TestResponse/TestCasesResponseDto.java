package com.InterviewSimulator.Interview.Simulator.DTO.Test.TestResponse;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TestCasesResponseDto {
   private String input;
   private String expected_output;

}
