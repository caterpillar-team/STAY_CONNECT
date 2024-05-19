package com.caterpillars.StayConnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caterpillars.StayConnect.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByRoleName(String roleName);
}
