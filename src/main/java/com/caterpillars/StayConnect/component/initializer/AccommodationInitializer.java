package com.caterpillars.StayConnect.component.initializer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.caterpillars.StayConnect.model.entities.Accommodation;
import com.caterpillars.StayConnect.model.entities.Category;
import com.caterpillars.StayConnect.model.entities.Grade;
import com.caterpillars.StayConnect.model.repository.AccommodationRepository;
import com.caterpillars.StayConnect.model.repository.CategoryRepository;
import com.caterpillars.StayConnect.model.repository.GradeRepository;

@Order(4)
@Component
public class AccommodationInitializer implements CommandLineRunner {

  @Autowired
  private AccommodationRepository accommodationRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private GradeRepository gradeRepository;

  @Override
  public void run(String... args) throws Exception {
    if (accommodationRepository.count() == 0) {
      saveTestData();
    }
  }

  private List<Accommodation> createTestData() {
    List<Accommodation> accommodations = new ArrayList<>();
    Category category = categoryRepository.findById((long) (Math.random() * 8) + 1).orElseThrow();
    Grade grade = gradeRepository.findById((long) (Math.random() * 5) + 1).orElseThrow();

    for (int i = 1; i <= 30; i++) {
      Accommodation accommodation = Accommodation.builder()
          .name("Accommodation " + i)
          .address("Address " + i)
          .latitude(37.0 + i)
          .longitude(127.0 + i)
          .category(category)
          .grade(grade)
          .build();
      accommodations.add(accommodation);
    }
    return accommodations;
  }

  private void saveTestData() {
    List<Accommodation> accommodations = createTestData();
    accommodationRepository.saveAll(accommodations);
  }

}