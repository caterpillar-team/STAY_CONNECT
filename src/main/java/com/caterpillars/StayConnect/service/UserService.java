package com.caterpillars.StayConnect.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.caterpillars.StayConnect.dto.UserSignUpDto;
import com.caterpillars.StayConnect.model.User;
import com.caterpillars.StayConnect.repository.UserRepository;

@Service
public class UserService {
  // private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public User signUp(UserSignUpDto signUpDto) {
    if (emailExist(signUpDto.getEmail())) {
      throw new RuntimeException("이미 해당 email로 등록된 계정이 존재합니다. : " + signUpDto.getEmail());
    }
    if (!signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }

    User user = new User();
    user.setRealName(signUpDto.getRealName());
    user.setBirth(signUpDto.getBirth());
    user.setGender(signUpDto.getGender());
    user.setPhoneNumber(signUpDto.getPhoneNumber());
    user.setEmail(signUpDto.getEmail());
    user.setUsername(signUpDto.getUsername());
    user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

    return userRepository.save(user);
  }

  private boolean emailExist(String email) {
    Optional<User> user = userRepository.findByEmail(email);
    return user.isPresent();
  }

}
