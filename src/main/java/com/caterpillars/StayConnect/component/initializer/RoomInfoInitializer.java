package com.caterpillars.StayConnect.component.initializer;

import java.util.NoSuchElementException;

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

    @Override
    public void run(String... args) throws Exception {
        final int TOTAL_ACCOMMODATIONS = 30;
        for (int i = 1; i <= TOTAL_ACCOMMODATIONS; i++) {
            Accommodation accommodation = accommodationRepository.findById((long) i)
                    .orElseThrow(NoSuchElementException::new);
            for (int j = 1; j <= 3; j++) { // 각 accommodation에 대해 3개의 roomInfo 생성
                RoomInfo roomInfo = RoomInfo.builder()
                        .accommodation(accommodation)
                        .roomType("Room " + ((i - 1) * 3 + j)) // 고유한 roomType 생성
                        .bedType("Bed " + ((i - 1) * 3 + j)) // 고유한 bedType 생성
                        .stayType("Stay " + ((i - 1) * 3 + j)) // 고유한 stayType 생성
                        .numberOfPeopleStandard((int) (Math.random() * 5) + 1) // 1에서 5명까지 수용 가능
                        .numberOfPeopleMax((int) (Math.random() * 5) + 1) // 1에서 5명까지 수용 가능
                        .checkInTime("14:00")
                        .checkOutTime("11:00")
                        .count((int) (Math.random() * 10) + 1) // 1에서 10명까지 수용 가능
                        .price(100 + (int) (Math.random() * 200)) // 100에서 300 사이의 가격
                        .build();
                roomInfoRepository.save(roomInfo);
            }
        }
    }
}