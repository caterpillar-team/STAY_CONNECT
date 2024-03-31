package com.caterpillars.StayConnect.repository;


import com.caterpillars.StayConnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
