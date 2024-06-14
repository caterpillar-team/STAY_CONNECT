package com.caterpillars.StayConnect.model.repository;

import com.caterpillars.StayConnect.model.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>{
    
}
