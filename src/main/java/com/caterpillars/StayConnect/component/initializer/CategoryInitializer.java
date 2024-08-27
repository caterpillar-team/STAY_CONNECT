package com.caterpillars.StayConnect.component.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.caterpillars.StayConnect.model.entities.Category;
import com.caterpillars.StayConnect.model.repository.CategoryRepository;

@Order(1)
@Component
public class CategoryInitializer implements CommandLineRunner {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        String[] categories = { "호텔", "모텔", "펜션", "게스트하우스", "리조트", "캠핑장", "민박", "아파트" };
        for (int i = 0; i < categories.length; i++) {
            if (!categoryRepository.findByName(categories[i]).isPresent()) {
                categoryRepository.save(new Category((long) (i + 1), categories[i], null));
            }
        }
    }
}
