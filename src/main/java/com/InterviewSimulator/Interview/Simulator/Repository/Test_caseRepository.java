package com.InterviewSimulator.Interview.Simulator.Repository;

import com.InterviewSimulator.Interview.Simulator.Entity.Test_cases;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Test_caseRepository extends JpaRepository<Test_cases, Long> {
    List<Test_cases> findByQuestion_id(Long QuestionId);

    List<Test_cases> findByQuestionIdAndIsHiddenFalse(Long QuestionId);
}
