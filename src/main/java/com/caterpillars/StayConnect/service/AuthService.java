package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.dto.UserSignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.caterpillars.StayConnect.model.User;
import com.caterpillars.StayConnect.repository.UserRepository;

import java.util.ArrayList;

@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User register(UserSignUpDto userSignUpDto){
        String encryptedPassword = passwordEncoder.encode(userSignUpDto.getPassword());

        User user = new User();
        user.setRealName(userSignUpDto.getRealName());
        user.setBirth(userSignUpDto.getBirth());
        user.setGender(userSignUpDto.getGender());
        user.setPhoneNumber(userSignUpDto.getPhoneNumber());
        user.setUsername(userSignUpDto.getUsername());
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
