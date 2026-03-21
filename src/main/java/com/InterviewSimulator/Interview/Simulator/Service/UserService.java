package com.InterviewSimulator.Interview.Simulator.Service;

import com.InterviewSimulator.Interview.Simulator.Entity.User;
import com.InterviewSimulator.Interview.Simulator.Repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Data
public class UserService {
   private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
