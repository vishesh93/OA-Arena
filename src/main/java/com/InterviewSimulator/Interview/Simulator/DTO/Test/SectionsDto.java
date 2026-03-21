package com.InterviewSimulator.Interview.Simulator.DTO.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SectionsDto {
    private String section_title;
    private String section_type;
    private String instructions;

    @JsonProperty("questions")
   private List<QuestionsDto> questionsDtoList = new ArrayList<>();


}
