package com.caterpillars.StayConnect.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caterpillars.StayConnect.model.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAll();

    Page<Review> findByRoomInfoAccommodationId(Long accommodationId, Pageable pageable);

    List<Review> findByRoomInfoAccommodationIdOrderByIdDesc(Long accommodationId);

}
