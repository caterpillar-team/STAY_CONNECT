package com.caterpillars.StayConnect.model.repository;

import com.caterpillars.StayConnect.model.entities.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    Optional<Accommodation> findByNameAndAddress(String name, String address);

    List<Accommodation> findAllByNameContaining(String name);
}
