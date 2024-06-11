package com.caterpillars.StayConnect.component.initializer;


import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.repository.AccommodationRepository;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class TestRoomInfoDataInitializer implements CommandLineRunner {

    @Autowired
    private RoomInfoRepository roomInfoRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Override
    public void run(String... args) throws Exception {

        Accommodation acc1 = accommodationRepository.findByNameAndAddress("Test Hotel Paradise","경상남도 진주시 장대로5번길 3 (장대동)")
                .orElseThrow(() -> new IllegalArgumentException("Accommodation not found"));

        RoomInfo room1 = RoomInfo.builder()
            .count(5)
            .numberOfPeopleMax(3)
            .numberOfPeopleStandard(2)
            .price(100000)
            .ckiTime("15:00")
            .ckoTime("11:00")
            .accommodation(acc1)
            .bedType("twin")
            .stayType("숙박")
            .roomType("스탠다드")
            .build();

    RoomInfo room2 = RoomInfo.builder()
            .count(5)
            .numberOfPeopleMax(3)
            .numberOfPeopleStandard(2)
            .price(50000)
            .ckiTime("13:00")
            .ckoTime("18:00")
            .accommodation(acc1)
            .bedType("twin")
            .stayType("대실")
            .roomType("스탠다드")
            .build();

    RoomInfo room3 = RoomInfo.builder()
            .count(5)
            .numberOfPeopleMax(3)
            .numberOfPeopleStandard(2)
            .price(130000)
            .ckiTime("15:00")
            .ckoTime("11:00")
            .accommodation(acc1)
            .bedType("twin")
            .stayType("숙박")
            .roomType("디럭스")
            .build();

    roomInfoRepository.save(room1);
    roomInfoRepository.save(room2);
    roomInfoRepository.save(room3);

  }
}