package com.caterpillars.StayConnect.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.caterpillars.StayConnect.dto.UserSignUpDto;
import com.caterpillars.StayConnect.model.Role;
import com.caterpillars.StayConnect.model.User;
import com.caterpillars.StayConnect.repository.RoleRepository;
import com.caterpillars.StayConnect.repository.UserRepository;

@Service
public class AuthService {
  // private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public User signUp(UserSignUpDto signUpDto) {
    Role role = roleRepository.findByRoleName("USER")
        .orElseGet(() -> roleRepository.save(new Role(null, "USER")));
    if (emailExist(signUpDto.getEmail())) {
      throw new RuntimeException("이미 해당 email로 등록된 계정이 존재합니다. : " + signUpDto.getEmail());
    }
    if (!signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }

    User user = User.builder()
        .username(signUpDto.getUsername())
        .password(passwordEncoder.encode(signUpDto.getPassword()))
        .email(signUpDto.getEmail())
        .birth(signUpDto.getBirth())
        .gender(signUpDto.getGender())
        .phoneNumber(signUpDto.getPhoneNumber())
        .realName(signUpDto.getRealName())
        .role(role)
        .build();

    return userRepository.save(user);
  }

  private boolean emailExist(String email) {
    Optional<User> user = userRepository.findByEmail(email);
    return user.isPresent();
  }

}