package com.caterpillars.StayConnect.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByRoomInfoAccommodationId(Long accommodationId, Pageable pageable);

    List<Review> findByRoomInfoAccommodationIdOrderByIdDesc(Long accommodationId);

    Optional<Review> findByUserAndRoomInfo(User user, RoomInfo roomInfo);
}
