package com.caterpillars.StayConnect.model.dto;
import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.Reservation;
import com.caterpillars.StayConnect.model.entities.Review;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String ckiTime;
    private String ckoTime;
    private int price;
    private List<Review> reviews;
    private List<Reservation> reservations;

}
