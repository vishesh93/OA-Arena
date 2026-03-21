package com.InterviewSimulator.Interview.Simulator.Controller;


import com.InterviewSimulator.Interview.Simulator.DTO.Test.TestDto;
import com.InterviewSimulator.Interview.Simulator.Service.GeminiService;

import com.InterviewSimulator.Interview.Simulator.Service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    TestService TestService;

    @GetMapping("/specialKey")
    public String specialKey() {
        return "yayyyyyy";
    }


    @GetMapping("/generateTest")
    public TestDto genrateTest() {
      return TestService.generateTest();
    }
}
