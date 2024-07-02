package com.caterpillars.StayConnect.model.repository;

import com.caterpillars.StayConnect.model.entities.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    Optional<Accommodation> findByNameAndAddress(String name, String address);

<<<<<<< HEAD
    List<Accommodation> findAllByNameContaining(String searchText);
=======
    List<Accommodation> findAllByNameContaining(String name);
>>>>>>> 910a0f3417227ed563f436cbf7567fa417a7a1b9
}
