package com.caterpillars.StayConnect.custom;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.caterpillars.StayConnect.model.entities.User;

public class CustomOAuth2UserDetails implements OAuth2User {

  private final User user;
  private final Map<String, Object> attributes;

  public CustomOAuth2UserDetails(User user, Map<String, Object> attributes) {
    this.user = user;
    this.attributes = attributes;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
  }

  @Override
  public String getName() {
    return attributes.get("name").toString();
  }

  public Long getId() {
    return user.getId();
  }
}
