package com.caterpillars.StayConnect.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDto {
    private long id;
    private String realName;
    private String roomType;
    private String bedType;
    private String accommodationName;
    private int price;
    private String payMethod;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}
