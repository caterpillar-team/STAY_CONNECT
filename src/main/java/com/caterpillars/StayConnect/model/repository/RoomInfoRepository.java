package com.caterpillars.StayConnect.model.repository;

import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomInfoRepository extends JpaRepository<RoomInfo, Long> {

    List<RoomInfo> findByAccommodation(Accommodation accommodation);

<<<<<<< Updated upstream
    List<RoomInfo> findByAccommodationId(Long accId);

=======
>>>>>>> Stashed changes
}
