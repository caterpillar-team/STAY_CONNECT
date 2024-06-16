package com.caterpillars.StayConnect.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByRoomInfo(RoomInfo roomInfo);
}
