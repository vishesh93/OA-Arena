package com.InterviewSimulator.Interview.Simulator.Repository;

import com.InterviewSimulator.Interview.Simulator.Entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {

    Optional<Test> findTopByDateOrderByCreatedAtDesc(LocalDate date);
}