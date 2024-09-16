package com.caterpillars.StayConnect.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.caterpillars.StayConnect.model.entities.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ChatController {

  @MessageMapping("/sendMessage")
  @SendTo("/topic/messages")
  public Message sendMessage(Message message) throws Exception {
    log.info(message.toString());
    return Message.builder().content(HtmlUtils.htmlEscapeHex(message.getContent())).build();
  }
}
