package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void temp(HttpServletRequest request) {
        User user = new User();

        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setEmail(user.getEmail());

        userRepository.save(user);
    }

}
