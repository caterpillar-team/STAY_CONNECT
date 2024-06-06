package com.caterpillars.StayConnect.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caterpillars.StayConnect.model.entities.Accommodation;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

  Optional<Accommodation> findByNameAndAddress(String name, String address);
}
