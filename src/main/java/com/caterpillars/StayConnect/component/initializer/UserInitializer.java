package com.caterpillars.StayConnect.component.initializer;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.caterpillars.StayConnect.model.entities.Role;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.RoleRepository;
import com.caterpillars.StayConnect.model.repository.UserRepository;

@Order(3)
@Component
public class UserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        final int TOTAL_USERS = 10;
        final String ADMIN_USERNAME = "admin";
        final String ADMIN_PASSWORD = "admin";
        final String ADMIN_REAL_NAME = "Admin";
        final String ADMIN_PHONE_NUMBER = "010-1234-5678";
        final String ADMIN_EMAIL = "admin@example.com";
        final String USER_PASSWORD_PREFIX = "user";
        final String USER_EMAIL_PREFIX = "user@example.com";
        final LocalDate USER_BIRTH_DATE = LocalDate.of(1995, 1, 1);

        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(NoSuchElementException::new);
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(NoSuchElementException::new);
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        for (int i = 0; i < TOTAL_USERS; i++) {
            User user = (i == 0) ? User.builder()
                    .username(ADMIN_USERNAME)
                    .password(passwordEncoder.encode(ADMIN_PASSWORD))
                    .realName(ADMIN_REAL_NAME)
                    .birth(LocalDate.of(1990, 1, 1))
                    .gender(true)
                    .phoneNumber(ADMIN_PHONE_NUMBER)
                    .email(ADMIN_EMAIL)
                    .role(adminRole)
                    .build()
                    : User.builder()
                            .username(USER_PASSWORD_PREFIX + i)
                            .password(passwordEncoder.encode(USER_PASSWORD_PREFIX + i))
                            .realName("User " + i)
                            .birth(USER_BIRTH_DATE)
                            .gender(true)
                            .phoneNumber("010-1234-567" + i)
                            .email("user" + i + "@" + USER_EMAIL_PREFIX)
                            .role(userRole)
                            .build();
            userRepository.save(user);
        }
    }
}