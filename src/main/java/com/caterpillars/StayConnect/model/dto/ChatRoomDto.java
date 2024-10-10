package com.caterpillars.StayConnect.model.dto;

import java.time.LocalDateTime;

import com.caterpillars.StayConnect.model.entities.ChatRoom;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDto {

    private Long id;

    private String userName;

    private LocalDateTime createdAt;

    public ChatRoomDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.userName = chatRoom.getUser().getUsername();
        this.createdAt = chatRoom.getCreatedAt();
    }
}
