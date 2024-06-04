package com.caterpillars.StayConnect.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.caterpillars.StayConnect.component.filter.JWTAuthenticationFilter;
import com.caterpillars.StayConnect.component.handler.JWTLoginSuccessHandler;
import com.caterpillars.StayConnect.component.handler.JWTLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        @Autowired
        private JWTAuthenticationFilter jwtAuthenticationFilter;

        @Autowired
        private JWTLoginSuccessHandler jwtLoginSuccessHandler;

        @Autowired
        private JWTLogoutSuccessHandler jwtLogoutSuccessHandler;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf((csrf) -> csrf.disable())

                                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                                                .requestMatchers("/", "/error/**").permitAll()
                                                .requestMatchers("/auth/**").not().authenticated()
                                                .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**").permitAll()
                                                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                // .exceptionHandling(exception -> exception
                                // .accessDeniedHandler(customAccessDeniedHandler))

                                .formLogin((formLogin) -> formLogin
                                                .loginPage("/auth/signin")
                                                .usernameParameter("username")
                                                .passwordParameter("password")
                                                .defaultSuccessUrl("/", true)
                                                .successHandler(jwtLoginSuccessHandler))

                                .logout(logout -> logout
                                                .logoutUrl("/user/logout")
                                                .logoutSuccessUrl("/")
                                                .logoutSuccessHandler(jwtLogoutSuccessHandler))

                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                ;

                return http.build();
        }
}
