package com.caterpillars.StayConnect.model.dto;

import java.time.LocalDateTime;

import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private User user;
    private RoomInfo roomInfo;
    private String contents;
    private LocalDateTime createdAt;
    private int rate;

}
