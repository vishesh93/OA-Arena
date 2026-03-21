package com.InterviewSimulator.Interview.Simulator.DTO.Test.TestResponse;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SectionResponseDto {
    private String sectionTitle;
    private String sectionType;
    private String instructions;
    private List<QuestionResponseDto> questions;
}