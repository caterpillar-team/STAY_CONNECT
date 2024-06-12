package com.caterpillars.StayConnect.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caterpillars.StayConnect.model.entities.Review;

public interface ReviewRepository extends JpaRepository <Review, Long>{
    

    
}
