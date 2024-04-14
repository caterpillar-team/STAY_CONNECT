package com.caterpillars.StayConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caterpillars.StayConnect.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
