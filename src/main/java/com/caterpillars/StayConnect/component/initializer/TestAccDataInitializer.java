package com.caterpillars.StayConnect.component.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.Category;
import com.caterpillars.StayConnect.model.entities.RoomInfo;
import com.caterpillars.StayConnect.model.repository.AccommodationRepository;
import com.caterpillars.StayConnect.model.repository.CategoryRepository;
import com.caterpillars.StayConnect.model.repository.RoomInfoRepository;

@Component
@Order(2)
public class TestAccDataInitializer implements CommandLineRunner {

        @Autowired
        private AccommodationRepository accommodationRepository;

        @Autowired
        private CategoryRepository categoryRepository;

        @Autowired
        private RoomInfoRepository roomInfoRepository;

        @Override
        public void run(String... args) throws Exception {

                Category hotelCategory = categoryRepository.findByName("Hotel")
                                .orElseGet(() -> categoryRepository.save(new Category(null, "Hotel", null)));

                Category retreatCategory = categoryRepository.findByName("Retreat")
                                .orElseGet(() -> categoryRepository.save(new Category(null, "Retreat", null)));

                Category bungalowCategory = categoryRepository.findByName("Bungalow")
                                .orElseGet(() -> categoryRepository.save(new Category(null, "Bungalow", null)));

                Accommodation acc1 = Accommodation.builder()
                                .name("Test Hotel Paradise")
                                .address("경상남도 진주시 장대로5번길 3 (장대동)")
                                .latitude(35.1919006911647)
                                .longitude(128.08842405491)
                                .category(hotelCategory)
                                .build();

                Accommodation acc2 = Accommodation.builder()
                                .name("Test Mountain Retreat")
                                .address("대구광역시 동구 동부로22길 24")
                                .latitude(35.8744776509678)
                                .longitude(128.626593577502)
                                .category(retreatCategory)
                                .build();

                Accommodation acc3 = Accommodation.builder()

                                .name("Test Beachside Bungalow")
                                .address("경상북도 구미시 인동36길 14-4 (진평동)")
                                .latitude(36.0937439897086)
                                .longitude(128.432015932589)
                                .category(bungalowCategory)
                                .build();

                RoomInfo room1 = RoomInfo.builder()
                                .count(5)
                                .numberOfPeopleMax(3)
                                .numberOfPeopleStandard(2)
                                .price(10000)
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
                                .price(10000)
                                .checkInTime("13:00")
                                .checkOutTime("18:00")
                                .accommodation(acc1)
                                .bedType("twin")
                                .stayType("대실")
                                .roomType("스탠다드")
                                .build();

                if (!accommodationRepository.findByNameAndAddress(acc1.getName(), acc1.getAddress()).isPresent()) {
                        accommodationRepository.save(acc1);
                }
                if (!accommodationRepository.findByNameAndAddress(acc2.getName(), acc2.getAddress()).isPresent()) {
                        accommodationRepository.save(acc2);
                }
                if (!accommodationRepository.findByNameAndAddress(acc3.getName(), acc3.getAddress()).isPresent()) {
                        accommodationRepository.save(acc3);
                }

                roomInfoRepository.save(room1);
                roomInfoRepository.save(room2);
        }
}