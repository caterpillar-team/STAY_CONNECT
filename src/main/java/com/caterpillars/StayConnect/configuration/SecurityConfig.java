package com.caterpillars.StayConnect.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.caterpillars.StayConnect.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        // @Bean
        // public UserDetailsService userDetailsService() {
        // UserDetails user = User.withUsername("user")
        // .password(passwordEncoder().encode("user"))
        // .roles("USER")
        // .build();

        // UserDetails admin = User.withUsername("admin")
        // .password(passwordEncoder().encode("admin"))
        // .roles("ADMIN")
        // .build();

        // UserDetails princess = User.withUsername("princess")
        // .password(passwordEncoder().encode("princess"))
        // .roles("ADMIN", "USER")
        // .build();

        // return new InMemoryUserDetailsManager(user, admin, princess);
        // } InMemoryUserDetailsService를 제외시키고 서버 실행시 test user를 만들도록
        // TestDataInitializer를 생성

        @Bean
        public UserDetailsService userDetailsService(UserRepository userRepository) {
                return username -> {
                        com.caterpillars.StayConnect.model.User user = userRepository.findByUsername(username)
                                        .orElseThrow(() -> new UsernameNotFoundException(
                                                        "User not found. : " + username));
                        return User.builder()
                                        .username(user.getUsername())
                                        .password(user.getPassword())
                                        .roles(user.getRole().getName())
                                        .build();
                };
        };

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf((csrf) -> csrf.disable())
                                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                                                .requestMatchers("/", "/auth/**").permitAll()
                                                .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**").permitAll()
                                                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())

                                .formLogin((formLogin) -> formLogin
                                                .loginPage("/auth/signin")
                                                .usernameParameter("username")
                                                .passwordParameter("password")
                                                .defaultSuccessUrl("/", true))

                                .logout(logout -> logout
                                                .logoutUrl("/user/logout")
                                                .logoutSuccessUrl("/"));

                return http.build();
        }
}
