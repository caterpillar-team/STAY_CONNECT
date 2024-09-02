package com.caterpillars.StayConnect.component.initializer;

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
  private GradeRepository gradeRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  private List<Grade> grades;
  private List<Category> categories;

  @Override
  public void run(String... args) throws Exception {
    // 데이터베이스에서 모든 Grade와 Category를 미리 로드
    grades = gradeRepository.findAll();
    categories = categoryRepository.findAll();

    final int TOTAL_ACCOMMODATIONS = 30;
    for (int i = 1; i <= TOTAL_ACCOMMODATIONS; i++) {
      Accommodation accommodation = Accommodation.builder()
          .category(getRandomCategory())
          .grade(getRandomGrade())
          .name("Accommodation " + i)
          .address("Location " + i)
          .latitude(37.5665 + (Math.random() * 0.1 - 0.05)) // 서울을 기준으로 ±0.05도
          .longitude(126.978 + (Math.random() * 0.1 - 0.05)) // 서울을 기준으로 ±0.05도
          .build();
      accommodationRepository.save(accommodation);
    }
  }

  private Grade getRandomGrade() {
    int randomIndex = (int) (Math.random() * grades.size());
    return grades.get(randomIndex);
  }

  private Category getRandomCategory() {
    int randomIndex = (int) (Math.random() * categories.size());
    return categories.get(randomIndex);
  }
}
