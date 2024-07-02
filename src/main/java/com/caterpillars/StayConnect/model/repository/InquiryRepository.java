package com.caterpillars.StayConnect.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.caterpillars.StayConnect.model.entities.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByRoomId(String roomId);
    Long countByRoomId(String roomId);
    List<Inquiry> findMessageBySender(String sender);

}
