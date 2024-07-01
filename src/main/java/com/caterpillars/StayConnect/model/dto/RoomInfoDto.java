package com.caterpillars.StayConnect.model.dto;

import java.util.List;

import com.caterpillars.StayConnect.model.entities.Accommodation;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class RoomInfoDto {

    private long id;
    private long accId;
    private String grade;
    private Accommodation accommodation;
    private String roomType;
    private String bedType;
    private String stayType;
    private int count;
    private int numberOfPeopleStandard;
    private int numberOfPeopleMax;
    private String checkInTime;
    private String checkOutTime;
    private int price;

    List<Integer> prices;

}
