package com.caterpillars.StayConnect.model.dto;

import lombok.*;

import java.time.LocalDateTime;


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
