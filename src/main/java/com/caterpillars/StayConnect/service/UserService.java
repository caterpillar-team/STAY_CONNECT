package com.caterpillars.StayConnect.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다."));
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> findByRealName(String realName) {
        return userRepository.findByRealName(realName);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void deleteUserById(Long id) {
        log.info("Deleting user with ID: " + id);
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public int getTotalUsers() {

        return userRepository.countTotalUsers();
    }
}
