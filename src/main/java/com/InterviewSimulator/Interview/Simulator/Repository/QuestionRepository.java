package com.InterviewSimulator.Interview.Simulator.Repository;

import com.InterviewSimulator.Interview.Simulator.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}