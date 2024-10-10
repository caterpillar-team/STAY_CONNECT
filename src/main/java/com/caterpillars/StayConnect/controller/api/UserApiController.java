package com.caterpillars.StayConnect.controller.api;

import java.util.Collection;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

  @GetMapping("/role")
  public ResponseEntity<?> getUserRole() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()
        || authentication instanceof AnonymousAuthenticationToken) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    String role = authorities.stream()
      .map(GrantedAuthority::getAuthority)
      .findFirst()
      .orElseThrow(() -> new RuntimeException("No role found for the user"));

    return ResponseEntity.ok(Collections.singletonMap("role", role));
  }
}
