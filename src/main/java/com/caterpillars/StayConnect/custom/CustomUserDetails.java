package com.caterpillars.StayConnect.custom;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.caterpillars.StayConnect.model.entities.Role;
import com.caterpillars.StayConnect.model.entities.User;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomUserDetails implements UserDetails {

  private User user;

  public CustomUserDetails(@NonNull User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  public Role getRole() {
    return user.getRole();
  }

  public Long getId() {
    return user.getId();
  }

  public User getUser() {
    return user;
  }
}
