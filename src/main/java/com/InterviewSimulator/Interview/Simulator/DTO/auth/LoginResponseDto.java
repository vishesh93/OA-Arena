package com.InterviewSimulator.Interview.Simulator.DTO.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginResponseDto{
    String jwt;
    private String username;
}
