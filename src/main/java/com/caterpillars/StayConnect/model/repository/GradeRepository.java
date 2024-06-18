package com.caterpillars.StayConnect.model.repository;

import com.caterpillars.StayConnect.model.entities.AccommodationGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationGradeRepository extends JpaRepository<AccommodationGrade, Long> {

}