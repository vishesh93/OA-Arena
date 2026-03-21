package com.InterviewSimulator.Interview.Simulator.Controller;


import com.InterviewSimulator.Interview.Simulator.DTO.auth.LoginDto;
import com.InterviewSimulator.Interview.Simulator.DTO.auth.LoginResponseDto;
import com.InterviewSimulator.Interview.Simulator.DTO.auth.SignupResponseDto;
import com.InterviewSimulator.Interview.Simulator.Security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final  AuthService authService;
    @PostMapping("/signup")
    public SignupResponseDto signup(@RequestBody LoginDto logindto) {
        System.out.println("signup hit");
      return  authService.signup(logindto);
    }

    @PostMapping("/login")
    public  LoginResponseDto login(@RequestBody LoginDto logindto) {
      return  authService.login(logindto);
    }
}
