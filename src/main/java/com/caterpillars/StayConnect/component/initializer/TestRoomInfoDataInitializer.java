package com.caterpillars.StayConnect.component.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.repository.AccommodationRepository;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;

// @Profile("dev")
@Order(4)
@Component
public class TestRoomInfoDataInitializer implements CommandLineRunner {

        @Autowired
        private RoomInfoRepository roomInfoRepository;

        @Autowired
        private AccommodationRepository accommodationRepository;

        @Override
        public void run(String... args) throws Exception {

                Accommodation acc1 = accommodationRepository
                                .findByNameAndAddress("Test Hotel Paradise", "경상남도 진주시 장대로5번길 3 (장대동)")
                                .orElseThrow(() -> new IllegalArgumentException("Accommodation not found"));
                Accommodation acc2 = accommodationRepository
                                .findByNameAndAddress("Test Mountain Retreat", "대구광역시 동구 동부로22길 24")
                                .orElseThrow(() -> new IllegalArgumentException("Accommodation not found"));
                Accommodation acc3 = accommodationRepository
                                .findByNameAndAddress("Test Beachside Bungalow", "경상북도 구미시 인동36길 14-4 (진평동)")
                                .orElseThrow(() -> new IllegalArgumentException("Accommodation not found"));

                RoomInfo room1 = RoomInfo.builder()
                                .count(5)
                                .numberOfPeopleMax(3)
                                .numberOfPeopleStandard(2)
                                .price(150000)
                                .checkInTime("15:00")
                                .checkOutTime("11:00")
                                .accommodation(acc1)
                                .bedType("twin")
                                .stayType("숙박")
                                .roomType("스탠다드")
                                .build();

                RoomInfo room2 = RoomInfo.builder()
                                .count(5)
                                .numberOfPeopleMax(3)
                                .numberOfPeopleStandard(2)
                                .price(100000)
                                .checkInTime("13:00")
                                .checkOutTime("18:00")
                                .accommodation(acc1)
                                .bedType("twin")
                                .stayType("대실")
                                .roomType("스탠다드")
                                .build();

                RoomInfo room3 = RoomInfo.builder()
                                .count(5)
                                .numberOfPeopleMax(3)
                                .numberOfPeopleStandard(2)
                                .price(190000)
                                .checkInTime("15:00")
                                .checkOutTime("11:00")
                                .accommodation(acc1)
                                .bedType("twin")
                                .stayType("숙박")
                                .roomType("디럭스")
                                .build();

                RoomInfo room4 = RoomInfo.builder()
                                .count(5)
                                .numberOfPeopleMax(3)
                                .numberOfPeopleStandard(2)
                                .price(80000)
                                .checkInTime("15:00")
                                .checkOutTime("11:00")
                                .accommodation(acc2)
                                .bedType("twin")
                                .stayType("숙박")
                                .roomType("스탠다드2")
                                .build();

                RoomInfo room5 = RoomInfo.builder()
                                .count(5)
                                .numberOfPeopleMax(3)
                                .numberOfPeopleStandard(2)
                                .price(50000)
                                .checkInTime("13:00")
                                .checkOutTime("18:00")
                                .accommodation(acc2)
                                .bedType("twin")
                                .stayType("대실")
                                .roomType("스탠다드2")
                                .build();

                RoomInfo room6 = RoomInfo.builder()
                                .count(5)
                                .numberOfPeopleMax(3)
                                .numberOfPeopleStandard(2)
                                .price(100000)
                                .checkInTime("15:00")
                                .checkOutTime("11:00")
                                .accommodation(acc2)
                                .bedType("twin")
                                .stayType("숙박")
                                .roomType("디럭스2")
                                .build();

                RoomInfo room7 = RoomInfo.builder()
                                .count(5)
                                .numberOfPeopleMax(3)
                                .numberOfPeopleStandard(2)
                                .price(250000)
                                .checkInTime("15:00")
                                .checkOutTime("11:00")
                                .accommodation(acc3)
                                .bedType("twin")
                                .stayType("숙박")
                                .roomType("스탠다드3")
                                .build();

                RoomInfo room8 = RoomInfo.builder()
                                .count(5)
                                .numberOfPeopleMax(3)
                                .numberOfPeopleStandard(2)
                                .price(170000)
                                .checkInTime("13:00")
                                .checkOutTime("18:00")
                                .accommodation(acc3)
                                .bedType("twin")
                                .stayType("대실")
                                .roomType("스탠다드3")
                                .build();

                RoomInfo room9 = RoomInfo.builder()
                                .count(5)
                                .numberOfPeopleMax(3)
                                .numberOfPeopleStandard(2)
                                .price(350000)
                                .checkInTime("15:00")
                                .checkOutTime("11:00")
                                .accommodation(acc3)
                                .bedType("twin")
                                .stayType("숙박")
                                .roomType("디럭스3")
                                .build();

                roomInfoRepository.save(room1);
                roomInfoRepository.save(room2);
                roomInfoRepository.save(room3);
                roomInfoRepository.save(room4);
                roomInfoRepository.save(room5);
                roomInfoRepository.save(room6);
                roomInfoRepository.save(room7);
                roomInfoRepository.save(room8);
                roomInfoRepository.save(room9);
        }
}