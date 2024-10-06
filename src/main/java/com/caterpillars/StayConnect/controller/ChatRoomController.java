package com.caterpillars.StayConnect.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caterpillars.StayConnect.custom.CustomUserDetails;
import com.caterpillars.StayConnect.model.entities.ChatRoom;
import com.caterpillars.StayConnect.model.repository.ChatRoomRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/chatRoom")
public class ChatRoomController {

  @Autowired
  private ChatRoomRepository chatRoomRepository;

  @PostMapping("")
  @Transactional()
  public ResponseEntity<String> createChatRoomIfNotExists() {

    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    Optional<ChatRoom> chatRoom = chatRoomRepository.findByUserId(userDetails.getId());
    if (chatRoom.isEmpty()) {
      ChatRoom newChatRoom = ChatRoom.builder()
          .user(userDetails.getUser())
          .createdAt(LocalDateTime.now())
          .build();
      chatRoom = Optional.of(newChatRoom);
      chatRoomRepository.save(newChatRoom);
      log.info("ChatRoom created for user id {}", userDetails.getId());
      return ResponseEntity.ok("ChatRoom created successfully");
    } else {
      log.info("ChatRoom already exists for user id {}", userDetails.getId());
      return ResponseEntity.ok("ChatRoom already exists");
    }
  }
}
