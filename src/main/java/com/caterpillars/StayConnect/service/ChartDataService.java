package com.caterpillars.StayConnect.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import com.caterpillars.StayConnect.model.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.Period;
import java.util.stream.Collectors;



@Service
public class ChartDataService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;




    public List<User> getAllUsers() {
    return userRepository.findAll();
    }

    public List<Review> getAllReviews() {
    return reviewRepository.findAll();
    }

    public List<Map<String,Object>> getAgeGroupCounts() {
        List<User> users = userRepository.findAll();

        Map<String, Long> ageGroupCounts = users.stream()
            .collect(Collectors.groupingBy(user -> getAgeGroup(user.getBirth()), Collectors.counting()));

        return ageGroupCounts.entrySet().stream()
            .map(entry -> { Map<String,Object> map = new HashMap<>();
            map.put("ageRange", entry.getKey());
            map.put("count",entry.getValue());
            return map;
            })
            .collect(Collectors.toList());
        }



    private String getAgeGroup(LocalDate birthDate) {
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        if (age < 20) return "10-19";
        else if (age < 30) return "20-29";
        else if (age < 40) return "30-39";
        else if (age < 50) return "40-49";
        else if (age < 60) return "50-59";
        else return "60+";
    }
    
}
