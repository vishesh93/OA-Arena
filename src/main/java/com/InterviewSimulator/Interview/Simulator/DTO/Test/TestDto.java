package com.InterviewSimulator.Interview.Simulator.DTO.Test;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
    String test_title;

    List<SectionsDto> sections;
}
