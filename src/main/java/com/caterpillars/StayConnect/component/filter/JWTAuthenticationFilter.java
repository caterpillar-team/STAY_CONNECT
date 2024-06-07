package com.caterpillars.StayConnect.component.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.caterpillars.StayConnect.component.provider.JWTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JWTokenProvider jwTokenProvider;

  @SuppressWarnings("null")
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = getTokenFromRequest(request);

    if (token != null && jwTokenProvider.validateToken(token)) {
      String username = jwTokenProvider.extractUsername(token);
      String role = jwTokenProvider.extractRole(token);
      List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null,
          authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      return Arrays.stream(cookies)
          .filter(cookie -> "jwt".equals(cookie.getName()))
          .map(Cookie::getValue)
          .findAny()
          .orElse(null);
    }
    return null;
  }
}
