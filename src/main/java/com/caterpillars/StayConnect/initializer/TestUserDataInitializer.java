package com.caterpillars.StayConnect.initializer;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.caterpillars.StayConnect.model.entities.Role;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.RoleRepository;
import com.caterpillars.StayConnect.model.repository.UserRepository;

@Component
public class TestUserDataInitializer implements CommandLineRunner {

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {
    Role userRole = roleRepository.findByName("USER").orElseGet(() -> roleRepository.save(new Role(null, "USER")));

    Role adminRole = roleRepository.findByName("ADMIN")
        .orElseGet(() -> roleRepository.save(new Role(null, "ADMIN")));

    User user = User.builder()
        .username("user")
        .password(passwordEncoder.encode("user"))
        .email("user@example.com")
        .birth(LocalDate.of(1990, 1, 1))
        .gender(true)
        .phoneNumber("010-9374-3238")
        .realName("Test User")
        .role(userRole)
        .build();

    User admin = User.builder()
        .username("admin")
        .password(passwordEncoder.encode("admin"))
        .email("admin@example.com")
        .birth(LocalDate.of(1998, 10, 12))
        .gender(false)
        .phoneNumber("010-2452-5484")
        .realName("Test Admin")
        .role(adminRole)
        .build();

    User princess = User.builder()
        .username("princess")
        .password(passwordEncoder.encode("princess"))
        .email("princess@example.com")
        .birth(LocalDate.of(1999, 12, 23))
        .gender(true)
        .phoneNumber("010-3949-9029")
        .realName("PRINCESS")
        .role(adminRole)
        .build();

    if (!userRepository.findByUsername(user.getUsername()).isPresent()) {
      userRepository.save(user);
    }
    if (!userRepository.findByUsername(admin.getUsername()).isPresent()) {
      userRepository.save(admin);
    }
    if (!userRepository.findByUsername(princess.getUsername()).isPresent()) {
      userRepository.save(princess);
    }
  }

}
