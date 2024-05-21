package com.caterpillars.StayConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caterpillars.StayConnect.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
