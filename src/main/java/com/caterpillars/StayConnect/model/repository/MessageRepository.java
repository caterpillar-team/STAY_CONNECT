package com.caterpillars.StayConnect.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caterpillars.StayConnect.model.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
