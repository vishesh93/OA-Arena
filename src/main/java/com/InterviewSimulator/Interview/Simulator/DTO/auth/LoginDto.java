package com.InterviewSimulator.Interview.Simulator.DTO.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LoginDto {
    private String username;
    private String password;
    private String email;

}
