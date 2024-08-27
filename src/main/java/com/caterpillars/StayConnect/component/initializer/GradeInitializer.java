package com.caterpillars.StayConnect.component.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.caterpillars.StayConnect.model.entities.Grade;
import com.caterpillars.StayConnect.model.repository.GradeRepository;

@Order(2)
@Component
public class GradeInitializer implements CommandLineRunner {
    @Autowired
    private GradeRepository gradeRepository;

    @Override
    public void run(String... args) throws Exception {
        String[] grades = {"1성급", "2성급", "3성급", "4성급", "5성급"};
        for (int i = 0; i < grades.length; i++) {
            if (!gradeRepository.findByGrade(grades[i]).isPresent()) {
                gradeRepository.save(new Grade((long) (i + 1), grades[i], null));
            }
        }
    }
}
