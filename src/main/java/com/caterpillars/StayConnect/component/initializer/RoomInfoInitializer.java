package com.caterpillars.StayConnect.component.initializer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.repository.AccommodationRepository;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;

@Order(5)
@Component
public class RoomInfoInitializer implements CommandLineRunner {

    @Autowired
    private RoomInfoRepository roomInfoRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    private List<Accommodation> accommodations;

    @Override
    public void run(String... args) throws Exception {

        accommodations = accommodationRepository.findAll();

        final int TOTAL_ACCOMMODATIONS = 30;
        for (int i = 1; i <= TOTAL_ACCOMMODATIONS; i++) {

            Accommodation accommodation = accommodations.get(i - 1);
            for (int j = 1; j <= 3; j++) {
                RoomInfo roomInfo = RoomInfo.builder()
                        .accommodation(accommodation)
                        .roomType("Room " + ((i - 1) * 3 + j))
                        .bedType("Bed " + ((i - 1) * 3 + j))
                        .stayType("Stay " + ((i - 1) * 3 + j))
                        .numberOfPeopleStandard((int) (Math.random() * 5) + 1)
                        .numberOfPeopleMax((int) (Math.random() * 5) + 1)
                        .checkInTime("14:00")
                        .checkOutTime("11:00")
                        .count((int) (Math.random() * 10) + 1)
                        .price(100 + (int) (Math.random() * 200)) 
                        .build();
                roomInfoRepository.save(roomInfo);
            }
        }
    }
}
