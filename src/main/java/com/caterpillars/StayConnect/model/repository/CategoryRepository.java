package com.caterpillars.StayConnect.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caterpillars.StayConnect.model.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
