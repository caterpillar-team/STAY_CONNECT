package com.caterpillars.StayConnect.component.initializer;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.caterpillars.StayConnect.model.entities.Role;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.RoleRepository;
import com.caterpillars.StayConnect.model.repository.UserRepository;

import jakarta.transaction.Transactional;

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
    @Transactional
    public void run(String... args) throws Exception {
        final int TOTAL_USERS = 10;
        final String ADMIN_USERNAME = "admin";
        final String ADMIN_PASSWORD = "admin";
        final String ADMIN_REAL_NAME = "Admin";
        final String ADMIN_PHONE_NUMBER = "010-1234-5678";
        final String ADMIN_EMAIL = "admin@example.com";
        final String USER_PASSWORD_PREFIX = "user";
        final String USER_EMAIL_PREFIX = "example.com";
        final LocalDate USER_BIRTH_DATE = LocalDate.of(1995, 1, 1);

        // Role 가져오기 또는 생성
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> createRole("ROLE_ADMIN"));
        Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> createRole("ROLE_USER"));

        // 사용자 생성
        for (int i = 0; i < TOTAL_USERS; i++) {
            User user = (i == 0)
                    ? createUser(ADMIN_USERNAME, ADMIN_PASSWORD, ADMIN_REAL_NAME, LocalDate.of(1990, 1, 1), true,
                            ADMIN_PHONE_NUMBER, ADMIN_EMAIL, adminRole)
                    : createUser(USER_PASSWORD_PREFIX + i, USER_PASSWORD_PREFIX + i, "User " + i, USER_BIRTH_DATE, true,
                            "010-1234-567" + i, "user" + i + "@" + USER_EMAIL_PREFIX, userRole);
            userRepository.save(user);
        }
    }

    private Role createRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }

    private User createUser(String username, String password, String realName, LocalDate birth, boolean gender,
            String phoneNumber, String email, Role role) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .realName(realName)
                .birth(birth)
                .gender(gender)
                .phoneNumber(phoneNumber)
                .email(email)
                .role(role)
                .build();
    }
}
