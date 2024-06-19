package com.caterpillars.StayConnect.component.initializer;

import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.Category;
import com.caterpillars.StayConnect.model.entities.Grade;
import com.caterpillars.StayConnect.model.repository.AccommodationRepository;
import com.caterpillars.StayConnect.model.repository.CategoryRepository;
import com.caterpillars.StayConnect.model.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Order(2)
public class TestAccDataInitializer implements CommandLineRunner {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Override
    public void run(String... args) throws Exception {

        Category hotelCategory = categoryRepository.findByName("Hotel")
                .orElseGet(() -> categoryRepository.save(new Category(null, "Hotel", new ArrayList<>())));

        Category retreatCategory = categoryRepository.findByName("Retreat")
                .orElseGet(() -> categoryRepository.save(new Category(null, "Retreat", new ArrayList<>())));

        Category bungalowCategory = categoryRepository.findByName("Bungalow")
                .orElseGet(() -> categoryRepository.save(new Category(null, "Bungalow", new ArrayList<>())));

        Grade grade5 = gradeRepository.findByGrade("5성급")
                .orElseGet(() -> gradeRepository.save(new Grade(null, "5성급", new ArrayList<>())));

        Accommodation acc1 = Accommodation.builder()
                .name("Test Hotel Paradise")
                .address("경상남도 진주시 장대로5번길 3 (장대동)")
                .latitude(35.1919006911647)
                .longitude(128.08842405491)
                .category(hotelCategory)
                .grade(grade5)
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

        if (!accommodationRepository.findByNameAndAddress(acc1.getName(), acc1.getAddress()).isPresent()) {
            accommodationRepository.save(acc1);
        }
        if (!accommodationRepository.findByNameAndAddress(acc2.getName(), acc2.getAddress()).isPresent()) {
            accommodationRepository.save(acc2);
        }
        if (!accommodationRepository.findByNameAndAddress(acc3.getName(), acc3.getAddress()).isPresent()) {
            accommodationRepository.save(acc3);
        }

    }
}