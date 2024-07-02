package com.caterpillars.StayConnect.model.repository;

import com.caterpillars.StayConnect.model.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByRoomInfoAccommodationId(Long accommodationId, Pageable pageable);

    List<Review> findByRoomInfoAccommodationIdOrderByIdDesc(Long accommodationId);

}
