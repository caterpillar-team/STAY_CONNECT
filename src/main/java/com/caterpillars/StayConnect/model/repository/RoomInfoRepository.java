package com.caterpillars.StayConnect.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.RoomInfo;

@Repository
public interface RoomInfoRepository extends JpaRepository<RoomInfo, Long> {

    List<RoomInfo> findByAccommodation(Accommodation accommodation);

    List<RoomInfo> findByAccommodationId(long accommodationId);
}
