package com.caterpillars.StayConnect.model.dto;

import com.caterpillars.StayConnect.model.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {

    private String id;
    private String roomId;




}
