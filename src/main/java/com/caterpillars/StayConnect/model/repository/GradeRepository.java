package com.caterpillars.StayConnect.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caterpillars.StayConnect.model.entities.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

}