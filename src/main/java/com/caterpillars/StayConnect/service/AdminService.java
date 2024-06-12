package com.caterpillars.StayConnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import com.caterpillars.StayConnect.model.repository.UserRepository;

import java.util.List;


@Service
public class AdminService {

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

    public List<Object[]> getAllUsersByAge() {
        return userRepository.findUserCountByAge();
    }
}
