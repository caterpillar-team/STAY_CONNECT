package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.model.entities.Role;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void temp(HttpServletRequest request) {
        User user = new User();

        user.setRealName(user.getRealName());
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPhoneNumber(user.getPhoneNumber());
        user.setEmail(user.getEmail());
        user.setBirth(user.getBirth());
        user.setRole(Role.builder().build());


        userRepository.save(user);
    }

}
