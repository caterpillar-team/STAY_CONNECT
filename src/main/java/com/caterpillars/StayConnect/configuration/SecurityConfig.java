package com.caterpillars.StayConnect.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.caterpillars.StayConnect.component.filter.JWTAuthenticationFilter;
import com.caterpillars.StayConnect.component.handler.JWTLoginSuccessHandler;
import com.caterpillars.StayConnect.component.handler.JWTLogoutSuccessHandler;
import com.caterpillars.StayConnect.component.handler.OAuth2UserLoginFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
        private JWTAuthenticationFilter jwtAuthenticationFilter;

        @Autowired
        private JWTLoginSuccessHandler jwtLoginSuccessHandler;

        @Autowired
        private JWTLogoutSuccessHandler jwtLogoutSuccessHandler;

        @Autowired
        private OAuth2UserLoginFailureHandler oAuth2UserLoginFailureHandler;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf((csrf) -> csrf.disable())
                                .cors(cors -> cors.configurationSource(
                                                corsConfigurationSource()))
                                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                                                .requestMatchers("/", "/accommodation/**", "/search").permitAll()
                                                .requestMatchers("/auth/**").not().authenticated()
                                                .requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/fonts/**")
                                                .permitAll()
                                                .requestMatchers("/user/**", "/chat/**", "/ws/**").hasAnyRole("USER", "ADMIN")
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .formLogin((formLogin) -> formLogin
                                                .loginPage("/auth/signin")
                                                .usernameParameter("username")
                                                .passwordParameter("password")
                                                .defaultSuccessUrl("/", true)
                                                .successHandler(jwtLoginSuccessHandler))
                                .oauth2Login(oauth -> oauth
                                                .defaultSuccessUrl("/", true)
                                                .failureUrl("/auth/signin?error=true")
                                                .successHandler(jwtLoginSuccessHandler)
                                                .failureHandler(oAuth2UserLoginFailureHandler))
                                .logout(logout -> logout
                                                .logoutUrl("/user/logout")
                                                .logoutSuccessUrl("/")
                                                .logoutSuccessHandler(jwtLogoutSuccessHandler))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.addAllowedOrigin("*"); // Allow all origins
                configuration.addAllowedMethod("*"); // Allow all HTTP methods
                configuration.addAllowedHeader("*"); // Allow all headers
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
