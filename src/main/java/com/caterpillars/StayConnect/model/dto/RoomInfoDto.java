package com.caterpillars.StayConnect.model.dto;
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

    private long accId;
    private Accommodation accommodation;
    private String name;
    private String roomType;
    private String bedType;
    private String stayType;
    private int count;
    private int numberOfPeopleStandard;
    private int numberOfPeopleMax;
    private String checkinTime;
    private String checkoutTime;
    private int price;

}
