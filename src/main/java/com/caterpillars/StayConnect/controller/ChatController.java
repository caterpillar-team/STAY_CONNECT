package com.caterpillars.StayConnect.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.caterpillars.StayConnect.custom.CustomUserDetails;
import com.caterpillars.StayConnect.service.ChatRoomService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChatController {

  @Autowired
  private ChatRoomService chatRoomService;

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping("/inquiry/message")
  public String sendMessage(@Payload String message, Principal principal) {

    CustomUserDetails userDetails = (CustomUserDetails) ((Authentication) principal).getPrincipal();

    if (chatRoomService.findChatRoomByUserId(userDetails.getId()).get().getAdmin() != null) {
      simpMessagingTemplate.convertAndSendToUser(userDetails.getUsername(), "/user/chat/message", message);
    } else {
      simpMessagingTemplate.convertAndSend("/inquiry/self", userDetails.getUsername() + ": " + message);
      simpMessagingTemplate.convertAndSend("/inquiry/claims", userDetails.getUsername() + ": " + message);

    }
    userDetails.getId();
    return userDetails.getUsername() + ": " + message;
  }
}