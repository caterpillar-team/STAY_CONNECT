package com.caterpillars.StayConnect.component.initializer;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.caterpillars.StayConnect.model.entities.Role;
import com.caterpillars.StayConnect.model.entities.User;
import com.caterpillars.StayConnect.model.repository.RoleRepository;
import com.caterpillars.StayConnect.model.repository.UserRepository;

@Order(1)
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
        for (int i = 1; i <= 10; i++) {
            String username = "user" + i;
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isEmpty()) {
                userRepository.save(User.builder()
                        .username(username)
                        .password(passwordEncoder.encode(username))
                        .realName(username)
                        .birth(LocalDate.now())
                        .gender(true)
                        .phoneNumber("010-1234-5678")
                        .email(username + "@gmail.com")
                        .messages(null)
                        .role(roleRepository.findByName("ROLE_USER").orElseGet(() -> createRole("ROLE_USER")))
                        .build());
            }
        }
        Optional<User> adminName = userRepository.findByUsername("admin");
        if (adminName.isEmpty()) {
            userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .realName("admin")
                    .birth(LocalDate.now())
                    .gender(true)
                    .phoneNumber("010-1234-5678")
                    .email("admin@gmail.com")
                    .messages(null)
                    .role(roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> createRole("ROLE_ADMIN")))
                    .build());
        }
    }

    private Role createRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }
}
