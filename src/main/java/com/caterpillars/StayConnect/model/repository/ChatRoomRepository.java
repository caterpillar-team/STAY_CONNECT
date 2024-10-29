package com.caterpillars.StayConnect.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caterpillars.StayConnect.model.entities.ChatRoom;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

  Optional<ChatRoom> findByUserId(Long userId);

  Optional<List<ChatRoom>> findAllByAdminId(Long id);

  Optional<List<ChatRoom>> findAllByAdminIdIsNull();
}
