package com.caterpillars.StayConnect.component.initializer;

import com.caterpillars.StayConnect.model.entities.AccommodationGrade;
import com.caterpillars.StayConnect.model.entities.Category;
import com.caterpillars.StayConnect.model.repository.AccommodationGradeRepository;
import com.caterpillars.StayConnect.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class TestAccGradeInitializer implements CommandLineRunner {

    @Autowired
    private AccommodationGradeRepository accommodationGradeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        Category hotelCategory = categoryRepository.findByName("Hotel")
                .orElseGet(() -> categoryRepository.save(new Category(null, "Hotel")));

//        AccommodationGrade grade1 = AccommodationGrade.builder()
//                .id(1)
//                .grade("1성급")
//                .category(hotelCategory)
//                .build();
//
//        AccommodationGrade grade2 = AccommodationGrade.builder()
//                .id(2)
//                .grade("2성급")
//                .category(hotelCategory)
//                .build();
//
//        AccommodationGrade grade3 = AccommodationGrade.builder()
//                .id(3)
//                .grade("3성급")
//                .category(hotelCategory)
//                .build();
//
//        AccommodationGrade grade4 = AccommodationGrade.builder()
//                .id(4)
//                .grade("4성급")
//                .category(hotelCategory)
//                .build();

        AccommodationGrade grade5 = AccommodationGrade.builder()
                .id(5)
                .grade("5성급")
                .category(hotelCategory)
                .build();

//        accommodationGradeRepository.save(grade1);
//        accommodationGradeRepository.save(grade2);
//        accommodationGradeRepository.save(grade3);
//        accommodationGradeRepository.save(grade4);
        accommodationGradeRepository.save(grade5);

    }
}
