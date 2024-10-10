package com.caterpillars.StayConnect.controller.api;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caterpillars.StayConnect.custom.PrincipalDetails;
import com.caterpillars.StayConnect.model.dto.ChatRoomDto;
import com.caterpillars.StayConnect.model.entities.ChatRoom;
import com.caterpillars.StayConnect.model.repository.ChatRoomRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class ChatRoomApiController {

  @Autowired
  private ChatRoomRepository chatRoomRepository;

  @PostMapping("/user/chatRoom")
  @Transactional()
  public ResponseEntity<String> createChatRoomIfNotExists() {

    PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    Optional<ChatRoom> chatRoom = chatRoomRepository.findByUserId(userDetails.getId());
    if (chatRoom.isEmpty()) {
      ChatRoom newChatRoom = ChatRoom.builder()
          .user(userDetails.getUser())
          .createdAt(LocalDateTime.now())
          .build();

      chatRoomRepository.save(newChatRoom);
      log.info("ChatRoom created for user id {}", userDetails.getId());
      return ResponseEntity.ok("ChatRoom created successfully");
    } else {
      log.info("ChatRoom already exists for user id {}", userDetails.getId());
      return ResponseEntity.ok("ChatRoom already exists");
    }
  }

  @GetMapping("/admin/chatRoom/list")
  @Transactional(readOnly = true)
  public ResponseEntity<Map<String, Object>> getChatRoomListAsJson() {
    PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();

    List<ChatRoomDto> assignedChatRooms = chatRoomRepository.findAllByAdminId(userDetails.getId())
        .orElseThrow(() -> new RuntimeException("ChatRoom not found for admin id " + userDetails.getId()))
        .stream()
        .map(chatRoom -> new ChatRoomDto(chatRoom))
        .collect(Collectors.toList());

    List<ChatRoomDto> unassignedChatRooms = chatRoomRepository.findAllByAdminIdIsNull()
        .orElseThrow(() -> new RuntimeException("ChatRoom not found"))
        .stream()
        .map(chatRoom -> new ChatRoomDto(chatRoom))
        .collect(Collectors.toList());

    Map<String, Object> response = new HashMap<>();
    response.put("assignedChatRooms", assignedChatRooms);
    response.put("unassignedChatRooms", unassignedChatRooms);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
