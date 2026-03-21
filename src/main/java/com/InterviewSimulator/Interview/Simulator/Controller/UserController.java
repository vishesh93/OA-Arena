package com.InterviewSimulator.Interview.Simulator.Controller;


import com.InterviewSimulator.Interview.Simulator.DTO.Test.TestResponse.TestResponseDto;
import com.InterviewSimulator.Interview.Simulator.DTO.TestSubmission.CodeRunDto;
import com.InterviewSimulator.Interview.Simulator.DTO.TestSubmission.TestResultDto;
import com.InterviewSimulator.Interview.Simulator.DTO.TestSubmission.TestSubmitRequestDto;
import com.InterviewSimulator.Interview.Simulator.Entity.User;
import com.InterviewSimulator.Interview.Simulator.Service.GroqService;
import com.InterviewSimulator.Interview.Simulator.Service.TestService;
import com.InterviewSimulator.Interview.Simulator.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final TestService testService;

    @Autowired
    GroqService groqService;

    @GetMapping("/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/test/today")
    public TestResponseDto testToday() {
        return testService.getTodayTest();
    }

    @PostMapping("/test/submit")
    public TestResultDto submitTest(@RequestBody TestSubmitRequestDto dto) {
        return testService.getTodayTestResult(dto);
    }
    @PostMapping("/test/run")
    public ResponseEntity<?> runTest(@RequestBody CodeRunDto dto) {
      return testService.RunCode(dto.getQuestion_id(), dto.getCode());
    }


}
