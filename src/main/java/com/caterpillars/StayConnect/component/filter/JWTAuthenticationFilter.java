package com.caterpillars.StayConnect.component.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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

/**
 * JWT 인증 필터 클래스입니다.
 * 요청에서 JWT 토큰을 추출하고 검증하여 인증을 설정합니다.
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JWTokenProvider jwTokenProvider;

  /**
   * 요청을 필터링하여 JWT 토큰을 검증하고 인증을 설정합니다.
   *
   * @param request     HTTP 요청
   * @param response    HTTP 응답
   * @param filterChain 필터 체인
   * @throws ServletException 서블릿 예외
   * @throws IOException      입출력 예외
   */
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    String token = getTokenFromRequest(request);

    if (token != null && jwTokenProvider.validateToken(token)) {
      String username = jwTokenProvider.extractUsername(token);
      String role = jwTokenProvider.extractRole(token);
      List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

      if (username != null && role != null) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null,
            authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    try {
      filterChain.doFilter(request, response);
    } finally {
      SecurityContextHolder.clearContext();
    }
  }

  /**
   * 요청에서 JWT 토큰을 추출합니다.
   *
   * @param request HTTP 요청
   * @return JWT 토큰
   */
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