package com.caterpillars.StayConnect.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caterpillars.StayConnect.model.entities.ChatRoom;
import com.caterpillars.StayConnect.model.repository.ChatRoomRepository;

@Service
public class ChatRoomService {

  @Autowired
  private ChatRoomRepository chatRoomRepository;

  @Autowired
  private UserService userService;

  public ChatRoom createChatRoom(Long userId) {
    ChatRoom newChatRoom = ChatRoom.builder()
        .user(userService.findById(userId))
        .build();

    return chatRoomRepository.save(newChatRoom);
  }

  public Optional<ChatRoom> findChatRoomByUserId(Long userId) {
    return chatRoomRepository.findByUserId(userId);
  }

  public ChatRoom assignAdminToChatRoom(Long chatRoomId, Long adminId) {
    ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
        .orElseThrow(() -> new RuntimeException("ChatRoom not found."));
    if (chatRoom != null) {
      chatRoom.setAdmin(userService.findById(adminId));
      return chatRoomRepository.save(chatRoom);
    }
    return null;
  }
}
