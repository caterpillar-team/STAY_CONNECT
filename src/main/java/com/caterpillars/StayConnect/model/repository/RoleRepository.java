package com.caterpillars.StayConnect.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caterpillars.StayConnect.model.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findByName(String roleName);
}
