package com.InterviewSimulator.Interview.Simulator.Security;

import com.InterviewSimulator.Interview.Simulator.DTO.auth.LoginDto;
import com.InterviewSimulator.Interview.Simulator.DTO.auth.LoginResponseDto;
import com.InterviewSimulator.Interview.Simulator.DTO.auth.SignupResponseDto;
import com.InterviewSimulator.Interview.Simulator.Entity.User;
import com.InterviewSimulator.Interview.Simulator.Repository.UserRepository;
import com.InterviewSimulator.Interview.Simulator.Enumerations.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
   private final AuthenticationManager authenticationManager;
   private final AuthUtil authUtil;


    public SignupResponseDto signup(LoginDto logindto) {



        Optional<User> user = userRepository.findByname(logindto.getUsername());



        if (user.isPresent()) {
            System.out.println("USER EXISTS");
            throw new IllegalArgumentException("Username already exists");
        }



        User newuser = User.builder()
                .name(logindto.getUsername())
                .email(logindto.getEmail())
                .role(Roles.USER)
                .password(passwordEncoder.encode(logindto.getPassword()))
                .build();

        System.out.println("STEP 4");

        User savedUser = userRepository.save(newuser);

        System.out.println("STEP 5: " + savedUser.getId());

        return new SignupResponseDto(savedUser.getUsername());
    }

    public LoginResponseDto login(LoginDto dto) {



        User user = userRepository.findByname(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));



        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            System.out.println("PASSWORD WRONG");
            throw new RuntimeException("Invalid password");
        }


        String token = null;
        try {
            token = authUtil.getJwtToken((user.getUsername()));

        } catch (Exception e) {
            System.out.println("JWT ERROR 🔥");
            e.printStackTrace();
        }


        return new LoginResponseDto(token,user.getName());
    }
}
