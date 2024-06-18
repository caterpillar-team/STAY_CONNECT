package com.caterpillars.StayConnect.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import com.caterpillars.StayConnect.model.entities.Inquery;

@Controller 
public class ChatController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Inquery sendMessage(Inquery inquery){
        inquery.setCreatedAt(LocalDateTime.now());
        return inquery;
    }
}
