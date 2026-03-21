package com.InterviewSimulator.Interview.Simulator.DTO.Test.TestResponse;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestResponseDto {

    private String Test_title;

    private List<SectionResponseDto> sections;
}
