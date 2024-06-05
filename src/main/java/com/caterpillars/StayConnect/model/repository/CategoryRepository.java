package com.caterpillars.StayConnect.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caterpillars.StayConnect.model.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  Optional<Category> findByName(String name);
}