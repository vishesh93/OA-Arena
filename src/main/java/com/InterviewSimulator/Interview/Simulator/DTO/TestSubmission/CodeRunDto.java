package com.InterviewSimulator.Interview.Simulator.DTO.TestSubmission;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeRunDto {
    private String code;
    private long Question_id;
}
