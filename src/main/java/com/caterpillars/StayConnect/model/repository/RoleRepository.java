package com.caterpillars.StayConnect.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caterpillars.StayConnect.model.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(String roleName);
}
