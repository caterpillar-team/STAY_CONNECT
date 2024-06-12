package com.caterpillars.StayConnect.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.caterpillars.StayConnect.component.jwt.filter.JwtAuthenticationFilter;
import com.caterpillars.StayConnect.component.jwt.handler.JWTLoginSuccessHandler;
import com.caterpillars.StayConnect.component.jwt.handler.JWTLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        @Autowired
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Autowired
        private JWTLoginSuccessHandler jwtLoginSuccessHandler;

        @Autowired
        private JWTLogoutSuccessHandler jwtLogoutSuccessHandler;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf((csrf) -> csrf.disable())
                                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                                                .requestMatchers("/", "/auth/**").permitAll()
                                                .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**").permitAll()
                                                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                                                .requestMatchers("/admin/**").permitAll()
                                                .anyRequest().authenticated())

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
                                // .authenticationManager(null)
                                // .authenticationProvider(jwTokenProvider)
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
