package com.caterpillars.StayConnect.service;

import com.caterpillars.StayConnect.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.caterpillars.StayConnect.model.User;
import com.caterpillars.StayConnect.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User register(UserDto userDto) throws Exception {
        if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User();
        user.setRealName(userDto.getRealName());
        user.setBirth(userDto.getBirth());
        user.setGender(userDto.getGender());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUsername(userDto.getUsername());
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }
}
