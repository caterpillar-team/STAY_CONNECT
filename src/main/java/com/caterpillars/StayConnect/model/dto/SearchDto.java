package com.caterpillars.StayConnect.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SearchDto {

    private String address;
    private Double latitude;
    private Double longitude;
    private int count;      // 잔여객실수
    private LocalDateTime checkIn;  // 입실날짜
    private int numOfPeople;    // 투숙인원

}
