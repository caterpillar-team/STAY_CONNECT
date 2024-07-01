package com.caterpillars.StayConnect.model.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.caterpillars.StayConnect.model.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  List<User> findByRealName (String realName);

  Optional<User> findByRealNameAndPhoneNumber(String username, String phoneNumber);

  @Query("SELECT YEAR(u.birth) as year, MONTH(u.birth) as month, COUNT(u) as count " +
    "FROM User u " +
    "GROUP BY YEAR(u.birth), MONTH(u.birth) " +
    "ORDER BY year, month")
  List<Object[]> findUserCountByAge();

  @Query("SELECT COUNT(u) FROM User u")
  int countTotalUsers();
}
