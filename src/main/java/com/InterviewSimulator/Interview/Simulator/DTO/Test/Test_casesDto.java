package com.InterviewSimulator.Interview.Simulator.DTO.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Test_casesDto {
    private String input;
    @JsonProperty("expected_output")
    private String Expected_output;
    public Boolean isHidden=false;
}
