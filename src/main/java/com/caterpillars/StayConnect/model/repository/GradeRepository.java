package com.caterpillars.StayConnect.model.repository;

import com.caterpillars.StayConnect.model.entities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    Optional<Grade> findByGrade(String name);
}