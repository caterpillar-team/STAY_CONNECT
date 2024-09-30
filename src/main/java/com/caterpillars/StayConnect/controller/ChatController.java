package com.caterpillars.StayConnect.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChatController {

  @MessageMapping("/sendMessage")
  @SendTo("/inquiry/messages")
  public String sendMessage(@Payload String message, Principal principal) {
    if (principal != null) {
      return principal.getName() + ": " + message;
    } else {
      log.warn("Unauthenticated user");
    }
    return null;
  }
}