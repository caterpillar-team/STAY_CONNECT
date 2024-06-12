package com.caterpillars.StayConnect.service;


import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.caterpillars.StayConnect.model.dto.UserSignUpDto;
import com.caterpillars.StayConnect.model.entities.Review;
import com.caterpillars.StayConnect.model.entities.Role;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.ReviewRepository;
import com.caterpillars.StayConnect.model.repository.RoleRepository;
import com.caterpillars.StayConnect.model.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private HttpServletRequest httpServletRequest;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ReviewRepository reviewRepository;

  public User signUp(UserSignUpDto signUpDto) {
    Role role = roleRepository.findByName("ROLE_USER")
        .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));
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

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("user not found username"));

    String requestURI = httpServletRequest.getRequestURI();
    if (requestURI.contains("/signin")) {
      return User.builder()
          .username(user.getUsername())
          .password(user.getPassword())
          .role(user.getRole())
          .build();
    }
    return user;
  }



  
}