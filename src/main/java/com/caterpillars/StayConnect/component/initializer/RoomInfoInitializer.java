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
  private AccommodationRepository accommodationRepository;

  @Autowired
  private RoomInfoRepository roomInfoRepository;

  @Override
  public void run(String... args) throws Exception {
    List<Accommodation> accommodations = accommodationRepository.findAll();
    if (roomInfoRepository.count() == 0) {
      for (Accommodation accommodation : accommodations) {
        for (int i = 1; i <= 3; i++) {
          RoomInfo roomInfo = RoomInfo.builder()
              .accommodation(accommodation)
              .roomType("Room Type " + i)
              .bedType("Bed Type " + i)
              .stayType("Stay Type " + i)
              .numberOfPeopleStandard(2)
              .numberOfPeopleMax(4)
              .checkInTime("14:00")
              .checkOutTime("12:00")
              .count(10)
              .price(100 * i)
              .build();
          roomInfoRepository.save(roomInfo);
        }
      }
    }
  }
}